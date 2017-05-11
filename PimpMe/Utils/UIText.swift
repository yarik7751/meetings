//
//  File.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 03.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import UIKit

extension UITextField {
  func addDoneButton() {
    let doneToolbar: UIToolbar = UIToolbar(frame: CGRect(x:0, y: 0, width: 320, height: 50))
    doneToolbar.barStyle = UIBarStyle.blackTranslucent

    let flexSpace = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.flexibleSpace, target: nil, action: nil)
    let done = UIBarButtonItem(title: NSLocalizedString("COMMON_Done", comment: ""), style: UIBarButtonItemStyle.done, target: self, action: #selector(doneAction))
    done.tintColor = UIColor.white
    doneToolbar.items = [flexSpace, done]
    doneToolbar.sizeToFit()
    self.inputAccessoryView = doneToolbar
  }

  func doneAction() {
    self.resignFirstResponder()
  }
}

extension UITextView {
  func addDoneButton() {
    let doneToolbar: UIToolbar = UIToolbar(frame: CGRect(x:0, y: 0, width: 320, height: 50))
    doneToolbar.barStyle = UIBarStyle.blackTranslucent

    let flexSpace = UIBarButtonItem(barButtonSystemItem: UIBarButtonSystemItem.flexibleSpace, target: nil, action: nil)
    let done = UIBarButtonItem(title: NSLocalizedString("COMMON_Done", comment: ""), style: UIBarButtonItemStyle.done, target: self, action: #selector(doneAction))
    done.tintColor = UIColor.white
    doneToolbar.items = [flexSpace, done]
    doneToolbar.sizeToFit()
    self.inputAccessoryView = doneToolbar
  }

  func doneAction() {
    self.resignFirstResponder()
  }
}
