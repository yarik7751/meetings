//
//  WomanSearchListViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 10.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanSearchListViewController: UIViewController {

  @IBOutlet weak var tableView: UITableView!
  var meetings = MeetingsStorage.shared.getMeetings()

  override func viewDidLoad() {
    super.viewDidLoad()
    let nib = UINib(nibName:String(describing: WomanMeetingTableViewCell.self), bundle: nil)
    tableView.register(nib, forCellReuseIdentifier: String(describing: WomanMeetingTableViewCell.self))
  }

}

extension WomanSearchListViewController: UITableViewDelegate, UITableViewDataSource {

  func numberOfSections(in tableView: UITableView) -> Int {
    return 1
  }

  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return meetings.count
  }

  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: WomanMeetingTableViewCell.self)) as! WomanMeetingTableViewCell
    cell.meeting = meetings[indexPath.row]
    return cell
  }

}
