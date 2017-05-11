//
//  DatePickerPopoverViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 03.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class DatePickerPopoverViewController: UIViewController {

  @IBOutlet private weak var datePicker: UIDatePicker!
  weak var delegate: DatePickerPopoverDelegate?

  @IBAction func done(_ sender: UIButton) {
    delegate?.datePicker(didFinishPicking: datePicker.date)
    dismiss(animated: true, completion: nil)
  }
}

protocol DatePickerPopoverDelegate: class {
  func datePicker(didFinishPicking date: Date)
}
