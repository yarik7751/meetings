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

class CityManager:NSObject, CLLocationManagerDelegate{
  
  private let locationManager = CLLocationManager()
  private let geoCoder = CLGeocoder()
  weak var delegate: CityManagerDelegate?

  override init() {
    super.init()
    locationManager.delegate = self
    locationManager.desiredAccuracy = kCLLocationAccuracyKilometer
    locationManager.requestWhenInUseAuthorization()
    locationManager.startUpdatingLocation()
  }
  
  func locationManager(_ manager: CLLocationManager, didFailWithError error: Error) {
    print("ERROR : \(error)")
  }
  
  func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
    locationManager.stopUpdatingLocation()
    geoCoder.reverseGeocodeLocation(locations.first!, completionHandler: {placemark, error in
      self.delegate?.cityManager(self, didUpdateCity: placemark?.last?.addressDictionary?["City"] as! String)
    })
  }
  
  func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
    guard status == .authorizedWhenInUse else { return }
    locationManager.startUpdatingLocation()
  }
  
}

protocol CityManagerDelegate: class {
  func cityManager(_ manager: CityManager, didUpdateCity city: String)
}


/*
 private static let sharedInstance = CityManager()
 
 fileprivate let locationManager:CLLocationManager = {
 let locationManager = CLLocationManager()
 locationManager.delegate = CityManager.shared
 locationManager.desiredAccuracy = kCLLocationAccuracyKilometer
 return locationManager
 }()
 
 fileprivate let geoCoder = CLGeocoder()
 
 static var shared:CityManager {
 return sharedInstance
 }
 
 func getCurrentCity() {
 locationManager.startUpdatingLocation()
 }
 
 */
