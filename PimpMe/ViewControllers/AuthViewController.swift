//
//  AuthViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class AuthViewController: UIViewController {
  @IBOutlet weak var emailTextField: UITextField!
  @IBOutlet weak var passwordTextField: UITextField!
  @IBOutlet weak var genderSelector: UISegmentedControl!

  
  @IBAction func login(_ sender: UIButton) {
    authenticate()
  }

  @IBAction func forgotPassword(_ sender: UIButton) {
  }
  
  @IBAction func endEditingTap(_ sender: UIControl) {
    view.endEditing(false)
  }
  
  func authenticate() {
    guard emailTextField.hasText, passwordTextField.hasText else {
      showAlert(withTitle: "COMMON_Error".localized, message: "AUTH_EmptyFields".localized)
      return
    }
    guard emailTextField.text!.isValidEmail else {
      showAlert(withTitle: "COMMON_Error".localized, message: "AUTH_NotEmail".localized)
      return
    }
    guard passwordTextField.text!.characters.count >= 6 else {
      showAlert(withTitle: "COMMON_Error".localized, message: "AUTH_ShortPassword".localized)
      return
    }    
    guard genderSelector.selectedSegmentIndex != UISegmentedControlNoSegment else {
      showAlert(withTitle: "COMMON_Error".localized, message: "AUTH_NoGender".localized)
      return
    }
    signUp()
  }
  
  private func signUp() {
    //TODO: - API Registration/logging should go here
    User.isAuthorized = true
    User.isMan = genderSelector.selectedSegmentIndex == 0 ? true : false
    let identifier = genderSelector.selectedSegmentIndex == 0 ? UIStoryboard.manIdentifier : UIStoryboard.womanIdentifier
    guard let vc = UIStoryboard.viewController(with: identifier) else {return}
    present(vc, animated: true, completion: nil)
  }
}


extension AuthViewController: UITextFieldDelegate {
  func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    if textField.isEqual(emailTextField) {
      view.endEditing(false)
    } else {
      authenticate()
    }
    return true
  }
}
