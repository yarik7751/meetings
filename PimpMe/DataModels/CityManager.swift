//
//  CityManager.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 02.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import CoreLocation
import MapKit

protocol CityManagerDelegate: class {
  func cityManager(didUpdateCity city: String)
}

class CityManager: NSObject {
  fileprivate let locationManager = CLLocationManager()
  fileprivate let geoCoder = CLGeocoder()
  fileprivate var city = ""
  weak var delegate: CityManagerDelegate?
  
  private static let sharedInstance = CityManager()
  static var shared: CityManager {
    return sharedInstance
  }
  
  private override init() {
    super.init()
    locationManager.delegate = self
    locationManager.desiredAccuracy = kCLLocationAccuracyHundredMeters
    locationManager.requestWhenInUseAuthorization()
    locationManager.startUpdatingLocation()
  }
  
  func updateCity() {
    locationManager.startUpdatingLocation()
  }
  
  func getCity() -> String {
    return city
  }
}

extension CityManager: CLLocationManagerDelegate {
  func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
    print("ERROR : \(error)")
  }
  
  func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
    locationManager.stopUpdatingLocation()
    geoCoder.reverseGeocodeLocation(locations.first!, completionHandler: {placemark, error in
      self.city = placemark?.last?.addressDictionary?["City"] as! String
      self.delegate?.cityManager(didUpdateCity: self.city)
    })
  }
  
  func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
    guard status == .authorizedWhenInUse else { return }
    locationManager.startUpdatingLocation()
  }
}
