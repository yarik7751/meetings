//
//  ManMeetingsViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 03.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class ManMeetingsTableViewController: UITableViewController {

  var height:CGFloat = 60.0
  var selectedIndexPath:IndexPath?
  var previousButton: RotationButton?
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.tableView.tableFooterView = UIView()
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
  
  @IBAction func done(_ sender: UIButton) {
    
  }
  
  override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
    if indexPath == selectedIndexPath { return 500.0}
    return height
  }
  
  private func expandCollapse(button: RotationButton) {
    if previousButton != button {
      previousButton?.buttonState = .collapsed
    }
    button.switchState()
    selectedIndexPath = selectedIndexPath == [0, button.tag] ? nil : [0, button.tag]
    previousButton = button
    tableView.beginUpdates()
    tableView.endUpdates()
  }
  
}
