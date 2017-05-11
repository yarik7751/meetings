//
//  ManPreviewMeetingViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 04.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class ManPreviewMeetingViewController: UIViewController {

  @IBOutlet private weak var girlAgeLabel: UILabel!
  @IBOutlet private weak var girlHeightLabel: UILabel!
  @IBOutlet private weak var girlHairColorLabel: UILabel!
  @IBOutlet private weak var placeLabel: UILabel!
  @IBOutlet private weak var dateLabel: UILabel!
  @IBOutlet private weak var amountLabel: UILabel!

  var meeting: Meeting!

  override func viewDidLoad() {
    super.viewDidLoad()
    girlAgeLabel.text = "От \(Int(meeting.preferredAge.start)) до \(Int(meeting.preferredAge.end)) лет"
    girlHeightLabel.text = "Рост от \(Int(meeting.preferredHeight.start)) до \(Int(meeting.preferredHeight.end)) см"
    girlHairColorLabel.text = meeting.preferredHairColor
    placeLabel.text = meeting.place.name
    dateLabel.text = meeting.time.start.stringValue
    amountLabel.text = "\(Int(meeting.present))$"
  }

  @IBAction func createMeeting(_ sender: UIButton) {
    MeetingsStorage.shared.add(meeting)
    navigationController?.popToRootViewController(animated: true)
  }

}
