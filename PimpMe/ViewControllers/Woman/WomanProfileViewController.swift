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
  
  let photos = [#imageLiteral(resourceName: "banana"), #imageLiteral(resourceName: "banana"), #imageLiteral(resourceName: "banana")]
  var activeTextField:UITextField!

  override func viewDidLoad() {
    super.viewDidLoad()
    startKeyboardObserving()
    let tap = UITapGestureRecognizer(target: self, action: #selector(endEditing))
    containerStackView.addGestureRecognizer(tap)
    pageControl.numberOfPages = photos.count
    pageControl.addObserver(self, forKeyPath: "currentPage", options: .new, context: nil)
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
  
}

extension WomanProfileViewController: KeyboardShowing {
  func keyboardWillHide(notification: Notification) {
    view.frame.origin.y = 0
  }
  
  func keyboardWillShow(notification: Notification) {
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
  
}

extension WomanProfileViewController: UIScrollViewDelegate {
  func scrollViewDidEndDecelerating(_ scrollView: UIScrollView){
    let pageWidth = scrollView.frame.width
    let currentPage = floor((scrollView.contentOffset.x - pageWidth / 2) / pageWidth) + 1
    
    self.pageControl.currentPage = Int(currentPage)
  }
}
