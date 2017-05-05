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
  
  static var name: String {
    get {
      return UserDefaults.standard.string(forKey: "name") ?? ""
    }
    set {
      UserDefaults.standard.set(newValue, forKey: "name")
    }
  }
  
  static var age: Int? {
    get {
      if let result = UserDefaults.standard.value(forKey: "age") as? Int {
        return result
      }
      return nil
    }
    set {
      UserDefaults.standard.set(newValue, forKey: "age")
    }
  }
  
  static var about: String {
    get {
      return UserDefaults.standard.string(forKey: "about") ?? NSLocalizedString("PROFILE_About" , comment: "")
    }
    set {
      UserDefaults.standard.set(newValue, forKey: "about")
    }
  }
  
  static var hairColor: String {
    get {
      return UserDefaults.standard.string(forKey: "hairColor") ?? ""
    }
    set {
      UserDefaults.standard.set(newValue, forKey: "hairColor")
    }
  }
  
  static var height: Int? {
    get {
      if let result = UserDefaults.standard.value(forKey: "height") as? Int {
        return result
      }
      return nil
    }
    set {
      UserDefaults.standard.set(newValue, forKey: "height")
    }
  }
}
  
