//
//  Keyboard.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import UIKit

@objc protocol KeyboardShowing: class {
  @objc optional func startKeyboardObserving()
  @objc optional func endKeyboardObserving()
  func keyboardWillShow(notification: Notification)
  func keyboardWillHide(notification: Notification)
}

extension KeyboardShowing where Self:UIViewController {

  func startKeyboardObserving() {
    NotificationCenter.default.addObserver(self, selector: #selector(Self.keyboardWillShow(notification:)), name: Notification.Name.UIKeyboardWillShow, object: nil)
    NotificationCenter.default.addObserver(self, selector: #selector(Self.keyboardWillHide(notification:)), name: Notification.Name.UIKeyboardWillHide, object: nil)
  }

  func endKeyboardObserving() {
    NotificationCenter.default.removeObserver(self, name: Notification.Name.UIKeyboardWillHide, object: nil)
    NotificationCenter.default.removeObserver(self, name: Notification.Name.UIKeyboardWillShow, object: nil)
  }

}
