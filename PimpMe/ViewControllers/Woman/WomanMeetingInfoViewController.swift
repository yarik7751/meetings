//
//  WomanMeetingInfoViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 02.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanMeetingInfoViewController: UIViewController {

  @IBOutlet weak var photoImageView: UIImageView!
  @IBOutlet weak var nameAgeLabel: UILabel!
  @IBOutlet weak var dateLabel: UILabel!
  @IBOutlet weak var placeLabel: UILabel!
  var meeting: Meeting!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    navigationItem.title = meeting.man.name
    nameAgeLabel.text = meeting.man.name
    dateLabel.text = meeting.time.start.stringValue
    placeLabel.text = meeting.place
    photoImageView.image = #imageLiteral(resourceName: "banana")
  }

  @IBAction func cancelMeeting(_ sender: UIButton) {
    let alert = UIAlertController(title: NSLocalizedString("COMMON_Cancel", comment: ""), message: NSLocalizedString("MEETING_INFO_CancelMeeting", comment: ""), preferredStyle: .alert)
    let yes = UIAlertAction(title: NSLocalizedString("COMMON_Yes", comment: ""), style: .default, handler: {
      _ in
      self.meeting.state = .pending
      self.navigationController?.popViewController(animated: true)
    })
    let no = UIAlertAction(title: NSLocalizedString("COMMON_No", comment: ""), style: .default, handler: nil)
    alert.addAction(yes)
    alert.addAction(no)
    present(alert, animated: true, completion: nil)
  }
}
