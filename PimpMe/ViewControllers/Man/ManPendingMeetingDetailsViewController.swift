//
//  ManPendingMeetingDetailsViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 10.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class ManPendingMeetingDetailsViewController: UIViewController {

  var meeting: Meeting!
  @IBOutlet weak var tableView: UITableView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    tableView.tableFooterView = UIView()
  }
  
}


extension ManPendingMeetingDetailsViewController: UITableViewDataSource, UITableViewDelegate {
  func numberOfSections(in tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return meeting.women?.count ?? 0
  }
  
  func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: WomanProfileTableViewCell.self)) as! WomanProfileTableViewCell
    cell.woman = meeting.women?[indexPath.row]
    return cell
  }
}
