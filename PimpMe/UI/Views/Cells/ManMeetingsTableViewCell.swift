//
//  ManMeetingsTableViewCell.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 04.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class ManMeetingsTableViewCell: UITableViewCell {

  @IBOutlet private weak var placeLabel: UILabel!
  @IBOutlet private weak var dateLabel: UILabel!
  @IBOutlet private weak var numberOfGirlsLabel: UILabel!
  @IBOutlet private weak var amountLabel: UILabel!
  
  var meeting: Meeting! {
    didSet {
      placeLabel.text = meeting.place
      dateLabel.text = meeting.time.start.stringValue
      numberOfGirlsLabel.text = "Откликнулось девушек: \(meeting.women?.count ?? 0)"
      amountLabel.text = "\(Int(meeting.present))$"
    }
  }

}
