//
//  ViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import UIKit

extension UIViewController {

  func showAlert(withTitle title: String, message: String, handler: ((UIAlertAction) -> Void)? = nil) {
    let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
    let action = UIAlertAction(title: "OK", style: .default, handler: handler)
    alert.addAction(action)
    present(alert, animated: true, completion: nil)
  }

}
