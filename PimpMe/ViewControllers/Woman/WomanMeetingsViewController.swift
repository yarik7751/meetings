//
//  WomanDatesViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanMeetingsViewController: UIViewController, Schedulable {

  @IBOutlet weak var womanHeaderView: WomanHeaderView!
  @IBOutlet weak var datesTableView: UITableView!
  var meetings = MeetingsStorage.shared.getMeetings()
  var scheduledMeetings = MeetingsStorage.shared.getScheduledMeetings()
  var hasScheduled = false

  override func viewDidLoad() {
    super.viewDidLoad()
    navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style: .plain, target: nil, action: nil)
    MeetingsStorage.shared.delegate = self
    datesTableView.tableFooterView = UIView()
    let nib = UINib(nibName:String(describing: WomanMeetingTableViewCell.self), bundle: nil)
    datesTableView.register(nib, forCellReuseIdentifier: String(describing: WomanMeetingTableViewCell.self))
    meetingStorageUpdated()
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
    infoVC.meeting = getMeeting(forIndexPath: indexPath)
  }

}

extension WomanMeetingsViewController: UITableViewDelegate, UITableViewDataSource {

  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
     return getNumberOfRows(forSection: section)
  }

  func numberOfSections(in tableView: UITableView) -> Int {
    return hasScheduled ? 2 : 1
  }

  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: WomanMeetingTableViewCell.self)) as! WomanMeetingTableViewCell
    cell.meeting = getMeeting(forIndexPath: indexPath)
    return cell
  }

  func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
    return getTitle(forSection: section)
  }
}

extension WomanMeetingsViewController: MeetingsStorageDelegate {
  func meetingStorageUpdated() {
    meetings = MeetingsStorage.shared.getMeetings()
    scheduledMeetings = MeetingsStorage.shared.getScheduledMeetings()
    hasScheduled = scheduledMeetings.isEmpty ? false : true
    datesTableView.reloadData()
  }
}
