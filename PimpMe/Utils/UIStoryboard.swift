//
//  UIStoryboard.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import UIKit

extension UIStoryboard {
  
  static let manIdentifier = "@Man"
  static let womanIdentifier = "@Woman"
  
  static func viewController(with identifier: String) -> UIViewController? {
    let components = identifier.components(separatedBy: "@")
    guard components.count > 1 else { return nil }
    let storyboard = UIStoryboard(name: components[1], bundle: nil)
    
    if components[0].characters.count > 0 {
      let controller = storyboard.instantiateViewController(withIdentifier: components[0])
      return controller
    } else {
      return storyboard.instantiateInitialViewController()
    }
  }
  
}
