//
//  AppDelegate.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit
import GooglePlaces
import GoogleMaps
import Firebase

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

  var window: UIWindow?

  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
    // Override point for customization after application launch.
    GMSServices.provideAPIKey("AIzaSyAAbvEmccssR__L2IauoLBaEd2f35cxVCI")
    GMSPlacesClient.provideAPIKey("AIzaSyBP6fCGb6C4P4w4Z9W6hNFEVutUZE7OMCQ")
    FIRApp.configure()
    guard User.isAuthorized else {return true}
    window?.rootViewController = UIStoryboard.viewController(with:
      User.isMan ? UIStoryboard.manIdentifier : UIStoryboard.womanIdentifier)
    return true
  }

}
