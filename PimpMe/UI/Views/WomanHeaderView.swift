//
//  WomanHeaderView.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanHeaderView: UIView {

  @IBOutlet weak var photoImageView: UIImageView!
  @IBOutlet weak var nameLabel: UILabel!
  @IBOutlet weak var ageLabel: UILabel!
  @IBOutlet var view: UIView!

  required init?(coder aDecoder: NSCoder) {
    super.init(coder: aDecoder)
    Bundle.main.loadNibNamed(String(describing: WomanHeaderView.self), owner: self, options: nil)
    view.frame = self.bounds
    view.autoresizingMask = [.flexibleWidth, .flexibleHeight]
    photoImageView.sd_setImage(with: URL(string: "https://cdn.pixabay.com/photo/2017/04/08/10/23/surfer-2212948_960_720.jpg"), placeholderImage: #imageLiteral(resourceName: "banana"))
    addSubview(view)
  }

  override func layoutSubviews() {
    super.layoutSubviews()
    photoImageView.layer.cornerRadius = (view.frame.height - 16.0) / 2.0
  }

}
