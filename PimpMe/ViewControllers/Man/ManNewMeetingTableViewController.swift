//
//  ManMeetingsViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 03.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit
import MARKRangeSlider

class ManNewMeetingTableViewController: UITableViewController {

  fileprivate var collapsedHeight: CGFloat = 60.0
  fileprivate var selectedIndexPath: IndexPath? = [0, 0]
  fileprivate var previousButton: RotationButton?
  fileprivate let placePicker = PlacePicker()
  fileprivate var startDate: Date?
  fileprivate var endDate: Date?

  /* Girl Outlets */
  @IBOutlet weak var expandGirlButton: RotationButton!
  @IBOutlet weak var ageRangeSlider: MARKRangeSlider!
  @IBOutlet weak var minimumAgeLabel: UILabel!
  @IBOutlet weak var maximumAgeLabel: UILabel!
  @IBOutlet weak var heightRangeSlider: MARKRangeSlider!
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
    navigationItem.title = NSLocalizedString("NEW_MEETING_Title", comment: "")
    tableView.tableFooterView = UIView()
    expandGirlButton.switchState()
    previousButton = expandGirlButton
    ageRangeSlider.setMinValue(18.0, maxValue: 55.0)
    ageRangeSlider.setLeftValue(18.0, rightValue: 25.0)
    ageRangeSlider.disableOverlapping = true
    ageRangeSlider.minimumDistance = 5.0
    heightRangeSlider.setMinValue(150.0, maxValue: 199.0)
    heightRangeSlider.setLeftValue(150.0, rightValue: 170.0)
    heightRangeSlider.disableOverlapping = true
    heightRangeSlider.minimumDistance = 5.0
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
    view.endEditing(false)
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
    guard placeLabel.text != NSLocalizedString("NEW_MEETING_Place", comment: "") else {showAlert(withTitle: NSLocalizedString("COMMON_Error", comment: ""),
                                                      message:NSLocalizedString("NEW_MEETING_NoPlace", comment: ""))
    return }

    guard let start = startDate, let end = endDate else {
      showAlert(withTitle: NSLocalizedString("COMMON_Error", comment: ""),
                message:NSLocalizedString("NEW_MEETING_NoTime", comment: ""))
      return
    }

    guard let amount = Double(amountTextField.text!) else {
      showAlert(withTitle: NSLocalizedString("COMMON_Error", comment: ""),
                message:NSLocalizedString("NEW_MEETING_NoPresent", comment: ""))
      return
    }
    let meeting = Meeting.createMeeting(at: placeLabel.text!, start: start, end: end, mansName: User.name, present: amount)
    meeting.preferredAge = Age(start: Double(ageRangeSlider.leftValue), end: Double(ageRangeSlider.rightValue))
    meeting.preferredHeight = Height(start: Double(heightRangeSlider.leftValue), end: Double(heightRangeSlider.rightValue))
    meeting.preferredHairColor = hairColorTextField.text
    let preview = UIStoryboard.viewController(with: String(describing: ManPreviewMeetingViewController.self) + "@ManNewMeeting") as! ManPreviewMeetingViewController
    preview.meeting = meeting
    navigationController?.pushViewController(preview, animated: true)
  }

}

extension ManNewMeetingTableViewController: UITextFieldDelegate {
  func textFieldShouldReturn(_ textField: UITextField) -> Bool {
    view.endEditing(false)
    return true
  }
}

extension ManNewMeetingTableViewController: PlacePickerDelegate {
  func placePicker(didFinishPicking place: String, address: String) {
    placeLabel.text = place
    adressLabel.text = address
  }
}

extension ManNewMeetingTableViewController: DatePickerPopoverDelegate {
  func datePicker(didFinishPicking date: Date) {
    startDate = date
    endDate = date.addingTimeInterval(17000)  //TODO: - Handle end date
    dateLabel.text = startDate!.timeIntervalSince1970.stringValue
  }
}

extension ManNewMeetingTableViewController: UIPopoverPresentationControllerDelegate {
  func adaptivePresentationStyle(for controller: UIPresentationController) -> UIModalPresentationStyle {
    return .none
  }
}
