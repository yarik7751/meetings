//
//  Constants.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

  
enum User {
  
  static var isAuthorized: Bool {
    get {
      return UserDefaults.standard.bool(forKey: "isAuthorized")
    }
    set {
      UserDefaults.standard.set(newValue, forKey: "isAuthorized")
    }
  }
  
  static var isMan: Bool {
    get {
      return UserDefaults.standard.bool(forKey: "isMan")
    }
    set {
      UserDefaults.standard.set(newValue, forKey: "isMan")
    }
  }

  
  
}
  
