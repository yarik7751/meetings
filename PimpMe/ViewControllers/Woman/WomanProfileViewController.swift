//
//  WomanProfileViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanProfileViewController: UIViewController {
  @IBOutlet weak var photoScrollView: PhotoScrollView!
  @IBOutlet weak var pageControl: UIPageControl!
  @IBOutlet weak var nameTextField: UITextField!
  @IBOutlet weak var ageTextField: UITextField!
  @IBOutlet weak var cityLabel: UILabel!
  @IBOutlet weak var heightTextField: UITextField!
  @IBOutlet weak var hairColorTextField: UITextField!
  @IBOutlet weak var containerStackView: UIStackView!

  let photo = Photo(id: 0, url: URL(string: "https://cdn.pixabay.com/photo/2017/04/08/10/23/surfer-2212948_960_720.jpg")!)
  var photos: [Photo]!
  var activeTextField: UITextField!

  override func viewDidLoad() {
    super.viewDidLoad()
    photos = [photo, photo, photo]
    let tap = UITapGestureRecognizer(target: self, action: #selector(endEditing))
    containerStackView.addGestureRecognizer(tap)
    pageControl.numberOfPages = photos.count
    pageControl.addObserver(self, forKeyPath: "currentPage", options: .new, context: nil)

    nameTextField.text = User.name
    hairColorTextField.text = User.hairColor
    if let age = User.age, let height = User.height {
      ageTextField.text = "\(age)"
      heightTextField.text = "\(height)"
    }

    CityManager.shared.delegate = self
  }

  override func viewDidAppear(_ animated: Bool) {
    super.viewDidAppear(animated)
    startKeyboardObserving()
  }

  override func viewWillDisappear(_ animated: Bool) {
    super.viewWillDisappear(animated)
    endKeyboardObserving()
  }

  override func viewDidLayoutSubviews() {
    super.viewDidLayoutSubviews()
    if !photoScrollView.isPopulated { photoScrollView.populate(with: photos) }
  }

  override func observeValue(forKeyPath keyPath: String?, of object: Any?, change: [NSKeyValueChangeKey : Any]?, context: UnsafeMutableRawPointer?) {
    guard keyPath == "currentPage" else { return }
  }

  func endEditing() {
    view.endEditing(false)
  }

  @IBAction func save(_ sender: UIButton) {
    guard nameTextField.hasText, ageTextField.hasText, heightTextField.hasText, hairColorTextField.hasText else {
      showAlert(withTitle: NSLocalizedString("COMMON_Error", comment: ""), message: NSLocalizedString("ERROR_EmptyFields", comment: ""))
      return
    }
    User.name = nameTextField.text!
    User.age = Int(ageTextField.text!)
    User.height = Int(heightTextField.text!)
    User.hairColor = hairColorTextField.text!
    showAlert(withTitle: NSLocalizedString("COMMON_Saved", comment: ""), message: NSLocalizedString("COMMON_PersonalInfoUpdated", comment: ""))
  }

}

extension WomanProfileViewController: KeyboardShowing {
  func keyboardWillHide(notification: Notification) {
    view.frame.origin.y = 0
  }

  func keyboardWillShow(notification: Notification) {
    guard view.frame.origin.y == 0 else { return }
    if let keyboardSize = (notification.userInfo?[UIKeyboardFrameBeginUserInfoKey] as? NSValue)?.cgRectValue {
      guard activeTextFieldBottomInSuperview() > view.frame.height - keyboardSize.height else { return }
      self.view.frame.origin.y -= keyboardSize.height - 50.0
    }
  }

  func activeTextFieldBottomInSuperview() -> CGFloat {
    return activeTextField.frame.origin.y + activeTextField.frame.height + photoScrollView.frame.height + 16.0
  }
}

extension WomanProfileViewController: UITextFieldDelegate {
  func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    view.endEditing(false)
    return true
  }

  func textFieldDidBeginEditing(_ textField: UITextField) {
    activeTextField = textField
  }

  func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
    guard textField.isEqual(ageTextField) || textField.isEqual(heightTextField) else { return true }
    let allowedCharacters = CharacterSet.decimalDigits.inverted
    let compSepByCharInSet = string.components(separatedBy: allowedCharacters)
    let numberFiltered = compSepByCharInSet.joined(separator: "")
    return string == numberFiltered
  }

}

extension WomanProfileViewController: UIScrollViewDelegate {
  func scrollViewDidEndDecelerating(_ scrollView: UIScrollView) {
    let pageWidth = scrollView.frame.width
    let currentPage = floor((scrollView.contentOffset.x - pageWidth / 2) / pageWidth) + 1

    self.pageControl.currentPage = Int(currentPage)
  }
}

extension WomanProfileViewController: CityManagerDelegate {
  func cityManager(didUpdateCity city: String) {
    cityLabel.text = city
  }
}
