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
  
  override func viewDidLoad() {
    super.viewDidLoad()
    meetings = Meeting.createFakeMeetings()
    datesTableView.tableFooterView = UIView()
  }
  
}

extension WomanMeetingsViewController: UITableViewDelegate, UITableViewDataSource {
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return meetings.count
  }
  
  func numberOfSections(in tableView: UITableView) -> Int {
    //TODO: - If has Scheduled return 2, if no return 1
    return 1
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: "womanDateCell") as! WomanMeetingTableViewCell
    cell.meeting = meetings[indexPath.row]
    return cell
  }
  
  
  
  
}
