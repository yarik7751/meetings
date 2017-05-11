//
//  ManProfileViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class ManProfileViewController: UIViewController {

  @IBOutlet weak var photoScrollView: PhotoScrollView!
  @IBOutlet weak var nameTextField: UITextField!
  @IBOutlet weak var ageTextField: UITextField!
  @IBOutlet weak var aboutTextView: UITextView!
  @IBOutlet weak var pageControl: UIPageControl!

  override func viewDidLoad() {
    super.viewDidLoad()
    aboutTextView.addDoneButton()
    ageTextField.addDoneButton()
    nameTextField.text = User.name
    aboutTextView.text = User.about
    guard let age = User.age else {return}
    ageTextField.text = "\(age)"
  }

  override func viewDidLayoutSubviews() {
    super.viewDidLayoutSubviews()
    aboutTextView.setContentOffset(CGPoint(x: 0.0, y: 0.0), animated: false)
    guard !photoScrollView.isPopulated else {return }
    photoScrollView.populate(with: [Photo(id: 0, url: URL(string: "https://cdn.pixabay.com/photo/2017/04/08/10/23/surfer-2212948_960_720.jpg")!)])
  }

  @IBAction func save(_ sender: UIButton) {
    User.name = nameTextField.text!
    User.age = Int(ageTextField.text!)
    User.about = aboutTextView.text
    showAlert(withTitle: NSLocalizedString("COMMON_Saved", comment: ""), message: NSLocalizedString("COMMON_PersonalInfoUpdated", comment: ""))
  }
}

extension ManProfileViewController: UITextFieldDelegate {
  func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    view.endEditing(false)
    return true
  }

  func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
    guard textField.isEqual(ageTextField) else { return true }
    let allowedCharacters = CharacterSet.decimalDigits.inverted
    let compSepByCharInSet = string.components(separatedBy: allowedCharacters)
    let numberFiltered = compSepByCharInSet.joined(separator: "")
    return string == numberFiltered
  }
}

extension ManProfileViewController: UITextViewDelegate {
  func textViewDidBeginEditing(_ textView: UITextView) {
    UIView.animate(withDuration: 0.3, animations: {
      self.view.frame.origin.y -= 150.0
    })
  }

  func textViewDidEndEditing(_ textView: UITextView) {
    UIView.animate(withDuration: 0.3, animations: {
      self.view.frame.origin.y = 0.0
    })
  }
}
