//
//  UIScrollView.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import UIKit

class PhotoScrollView: UIScrollView {
  var isPopulated = false
  
  func populate(with photos: [UIImage]) {
    self.contentSize = CGSize(width: self.frame.width * CGFloat(photos.count), height: self.frame.height)
    for (index, photo) in photos.enumerated() {
      let img = UIImageView(frame: CGRect(x:self.frame.width * CGFloat(index), y:0, width:self.frame.width, height:self.frame.height))
      img.image = photo.image(with: CGSize(width: self.frame.width, height: self.frame.height))
      self.addSubview(img)
    }
    self.isPopulated = true
  }
}
