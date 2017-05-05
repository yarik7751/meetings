//
//  ManMeetingsViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 04.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class ManMeetingsViewController: UIViewController, Schedulable {

  @IBOutlet weak var tableView: UITableView!
  var meetings = MeetingsStorage.shared.getMeetings()
  var scheduledMeetings = MeetingsStorage.shared.getScheduledMeetings()
  var hasScheduled = false
  
  override func viewDidLoad() {
    super.viewDidLoad()
    MeetingsStorage.shared.delegate = self
    navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .done, target: nil, action: nil)
    tableView.tableFooterView = UIView()
    meetingStorageUpdated()
  }
}

extension ManMeetingsViewController: UITableViewDelegate, UITableViewDataSource {
  func numberOfSections(in tableView: UITableView) -> Int {
    return hasScheduled ? 2 : 1
  }
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return getNumberOfRows(forSection: section)
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: ManMeetingsTableViewCell.self)) as! ManMeetingsTableViewCell
    cell.meeting = getMeeting(forIndexPath: indexPath)
    return cell
  }
  
  func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
    return getTitle(forSection: section)
  }
  
  func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
    let deleteAction = UITableViewRowAction(style: .default, title: NSLocalizedString("COMMON_Cancel", comment: ""), handler: { (action, indexPath) in
      let meeting = self.getMeeting(forIndexPath: indexPath)
      self.confirmCancel(meeting)
    })
    return [deleteAction]
  }

  
  private func confirmCancel(_ meeting: Meeting) {
    let alertVC = UIAlertController(title: NSLocalizedString("COMMON_Cancel", comment: ""), message: NSLocalizedString("MEETING_INFO_CancelMeeting", comment: ""), preferredStyle: .alert)
    let yes = UIAlertAction(title: NSLocalizedString("COMMON_Yes", comment: ""), style: .default, handler: {
      _ in meeting.cancel()
    })
    let no = UIAlertAction(title: NSLocalizedString("COMMON_No", comment: ""), style: .default, handler: nil)
    alertVC.addAction(yes)
    alertVC.addAction(no)
    present(alertVC, animated: true, completion: nil)
  }
}

extension ManMeetingsViewController: MeetingsStorageDelegate {
  func meetingStorageUpdated() {
    meetings = MeetingsStorage.shared.getMeetings()
    scheduledMeetings = MeetingsStorage.shared.getScheduledMeetings()
    hasScheduled = scheduledMeetings.count > 0 ? true : false
    tableView.reloadData()
  }
}
