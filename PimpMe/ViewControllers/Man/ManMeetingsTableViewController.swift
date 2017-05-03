//
//  ManMeetingsViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 03.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit
import MARKRangeSlider

class ManMeetingsTableViewController: UITableViewController {

  var collapsedHeight:CGFloat = 60.0
  var selectedIndexPath:IndexPath? = [0,0]
  var previousButton: RotationButton?
  let placePicker = PlacePicker()
  
  /* Girl Outlets */
  @IBOutlet weak var expandGirlButton: RotationButton!
  @IBOutlet weak var ageRangeSlider: MARKRangeSlider!
  @IBOutlet weak var minimumAgeLabel: UILabel!
  @IBOutlet weak var maximumAgeLabel: UILabel!
  @IBOutlet weak var heightTextField: UITextField!
  @IBOutlet weak var hairColorTextField: UITextField!
  
  /* Location Outlets */
  @IBOutlet weak var placeLabel: UILabel!
  @IBOutlet weak var adressLabel: UILabel!
  @IBOutlet weak var dateLabel: UILabel!
  
  /* Present Outlets */
  @IBOutlet weak var amountTextField: UITextField!
  
  
  override func viewDidLoad() {
    super.viewDidLoad()
    placePicker.delegate = self
    setupUI()
  }
  
  private func setupUI() {
    tableView.tableFooterView = UIView()
    expandGirlButton.switchState()
    previousButton = expandGirlButton
    ageRangeSlider.setMinValue(18.0, maxValue: 55.0)
    ageRangeSlider.setLeftValue(18.0, rightValue: 25.0)
    ageRangeSlider.disableOverlapping = true
    ageRangeSlider.minimumDistance = 5.0
    heightTextField.addDoneButton()
    amountTextField.addDoneButton()
  }
  
  @IBAction func expandCollapseFirstCell(_ sender: RotationButton) {
    expandCollapse(button: sender)
  }

  @IBAction func expandCollapseSecondCell(_ sender: RotationButton) {
    expandCollapse(button: sender)
  }
  
  @IBAction func expandCollapseThird(_ sender: RotationButton) {
    expandCollapse(button: sender)
  }

  @IBAction func pickDate(_ sender: UIButton) {
    let datePicker = UIStoryboard.viewController(with: String(describing: DatePickerPopoverViewController.self) + "@Popovers") as! DatePickerPopoverViewController
    datePicker.modalPresentationStyle = .popover
    datePicker.popoverPresentationController!.delegate = self
    datePicker.popoverPresentationController!.sourceView = sender
    datePicker.popoverPresentationController?.backgroundColor = UIColor.white
    datePicker.delegate = self
    present(datePicker, animated: true, completion: nil)
  }
  
  @IBAction func pickPlace(_ sender: UIButton) {
    placePicker.pickPlace()
  }
  
  @IBAction func ageChanged(_ sender: MARKRangeSlider) {
    minimumAgeLabel.text = String(describing: Int(sender.leftValue))
    maximumAgeLabel.text = String(describing: Int(sender.rightValue))
  }
  
  override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    if indexPath == selectedIndexPath {
      switch indexPath.row {
      case 1:
        return 330.0
      case 2:
        return 228.0
      default:
        return 380.0
      }
    }
    return collapsedHeight
  }
  
  private func expandCollapse(button: RotationButton) {
    if previousButton != button {
      previousButton?.buttonState = .collapsed
    }
    button.switchState()
    selectedIndexPath = selectedIndexPath == [0, button.tag] ? nil : [0, button.tag]
    previousButton = button
    tableView.beginUpdates()
    tableView.endUpdates()
  }
  
  @IBAction func previewMeeting(_ sender: UIButton) {
    
  }
  
}

extension ManMeetingsTableViewController: UITextFieldDelegate {
  func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    view.endEditing(false)
    return true
  }
}

extension ManMeetingsTableViewController: PlacePickerDelegate {
  func placePicker(didFinishPicking place: String, address: String) {
    placeLabel.text = place
    adressLabel.text = address
  }
}

extension ManMeetingsTableViewController: DatePickerPopoverDelegate {
  func datePicker(didFinishPicking date: Date) {
    dateLabel.text = date.stringValue
  }
}

extension ManMeetingsTableViewController: UIPopoverPresentationControllerDelegate {
  func adaptivePresentationStyle(for controller: UIPresentationController) -> UIModalPresentationStyle {
    return .none
  }
}
