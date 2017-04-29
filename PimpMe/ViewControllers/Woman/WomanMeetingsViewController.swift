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
    scheduledMeetings = Meeting.getScheduled(from: meetings)
    hasScheduled = scheduledMeetings.count > 0 ? true : false
    datesTableView.tableFooterView = UIView()
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
    if hasScheduled {
      cell.meeting = indexPath.section == 0 ? scheduledMeetings[indexPath.row] : meetings[indexPath.row]
    } else {
      cell.meeting = meetings[indexPath.row]
    }
    return cell
  }
  
  func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
    guard hasScheduled else { return MeetingState.pending.rawValue.localized }
    return section == 0 ? MeetingState.scheduled.rawValue.localized : MeetingState.pending.rawValue.localized
  }
  
  
  
  
}
