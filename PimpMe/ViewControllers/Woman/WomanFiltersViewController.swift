//
//  WomanFiltersViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit
import MARKRangeSlider

class WomanFiltersViewController: UIViewController {

  @IBOutlet weak var minimumAmountTextField: UITextField!
  @IBOutlet weak var ageRangeSlider: MARKRangeSlider!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    ageRangeSlider.backgroundColor = UIColor.clear
    ageRangeSlider.setMinValue(18.0, maxValue: 90.0)
    ageRangeSlider.setLeftValue(30.0, rightValue: 40.0)
    ageRangeSlider.minimumDistance = 5.0
    ageRangeSlider.disableOverlapping = true
  }

  @IBAction func ageChanged(_ sender: MARKRangeSlider) {
    print("left: \(sender.leftValue), right: \(sender.rightValue)")
  }
}

extension WomanFiltersViewController: UITextFieldDelegate {
  func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    view.endEditing(false)
    return true
  }
}
