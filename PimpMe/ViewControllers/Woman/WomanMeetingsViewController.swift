//
//  WomanDatesViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanMeetingsViewController: UIViewController {

  @IBOutlet weak var womanHeaderView: WomanHeaderView!
  @IBOutlet weak var datesTableView: UITableView!
  var meetings: [Meeting]!
  fileprivate var scheduledMeetings: [Meeting]!
  fileprivate var hasScheduled = false
  
  override func viewDidLoad() {
    super.viewDidLoad()
    meetings = Meeting.createFakeMeetings()
    navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: nil, action: nil)
    scheduledMeetings = Meeting.getScheduled(from: meetings)
    hasScheduled = scheduledMeetings.count > 0 ? true : false
    datesTableView.tableFooterView = UIView()
  }
  
  override func viewWillAppear(_ animated: Bool) {
    super.viewWillAppear(animated)
    if let indexPath = datesTableView.indexPathForSelectedRow {
      datesTableView.deselectRow(at: indexPath, animated: true)
    }
  }
  
  override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
    guard segue.identifier == "showMeetingInfo" else { return }
    let infoVC = segue.destination as! WomanMeetingInfoViewController
    let indexPath = datesTableView.indexPathForSelectedRow!
    infoVC.meeting = getAppropriateMeeting(forIndexPath: indexPath)
  }
  
  fileprivate func getAppropriateMeeting(forIndexPath indexPath: IndexPath) -> Meeting {
    if hasScheduled {
      return indexPath.section == 0 ? scheduledMeetings[indexPath.row] : meetings[indexPath.row]
    } else {
      return meetings[indexPath.row]
    }
  }
  
}

extension WomanMeetingsViewController: UITableViewDelegate, UITableViewDataSource {
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    guard hasScheduled else {return meetings.count }
    return section == 0 ? scheduledMeetings.count : meetings.count - scheduledMeetings.count
  }
  
  func numberOfSections(in tableView: UITableView) -> Int {
    return hasScheduled ? 2 : 1
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: ReuseIdentifiers.womanDateCell) as! WomanMeetingTableViewCell
    cell.meeting = getAppropriateMeeting(forIndexPath: indexPath)
    return cell
  }
  
  func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
    guard hasScheduled else { return MeetingState.pending.rawValue.localized }
    return section == 0 ? MeetingState.scheduled.rawValue.localized : MeetingState.pending.rawValue.localized
  }
  
}
