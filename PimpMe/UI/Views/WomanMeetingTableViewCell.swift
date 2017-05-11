//
//  WomanMeetingTableViewCell.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanMeetingTableViewCell: UITableViewCell {

  @IBOutlet private weak var photoImageView: UIImageView!
  @IBOutlet private weak var nameAgeLabel: UILabel!
  @IBOutlet private weak var placeLabel: UILabel!
  @IBOutlet private weak var dateLabel: UILabel!
  @IBOutlet private weak var amountLabel: UILabel!

  var meeting: Meeting! {
    didSet {
      photoImageView.sd_setImage(with: URL(string: "https://cdn.pixabay.com/photo/2017/04/08/10/23/surfer-2212948_960_720.jpg"), placeholderImage: #imageLiteral(resourceName: "banana"))
      nameAgeLabel.text = meeting.man.name
      placeLabel.text = meeting.place.name
      dateLabel.text = meeting.time.start.stringValue
      amountLabel.text = "\(Int(meeting.present))$"
    }
  }
}
