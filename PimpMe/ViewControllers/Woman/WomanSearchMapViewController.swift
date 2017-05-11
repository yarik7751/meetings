//
//  WomanSearchMapViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 10.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit
import GoogleMaps

class WomanSearchMapViewController: UIViewController {

  @IBOutlet var mapView: GMSMapView!
  let locationManager = CLLocationManager()

  override func viewDidLoad() {
    super.viewDidLoad()
    mapView.padding = UIEdgeInsets(top: 0.0, left: 0.0, bottom: self.tabBarController!.tabBar.frame.height, right: 0.0)
    locationManager.delegate = self
    locationManager.requestWhenInUseAuthorization()
  }
}

extension WomanSearchMapViewController: CLLocationManagerDelegate {
  func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
    if let location = locations.first {
      mapView.camera = GMSCameraPosition(target: location.coordinate, zoom: 15, bearing: 0, viewingAngle: 0)
      locationManager.stopUpdatingLocation()
    }
  }

  func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus) {
    if status == .authorizedWhenInUse {
      locationManager.startUpdatingLocation()
      mapView.isMyLocationEnabled = true
      mapView.settings.myLocationButton = true
    }
  }
}
