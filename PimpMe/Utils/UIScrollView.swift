//
//  UIScrollView.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import UIKit
import SDWebImage

class PhotoScrollView: UIScrollView {
  var isPopulated = false
  
  func populate(with photos: [Photo]) {
    self.contentSize = CGSize(width: self.frame.width * CGFloat(photos.count), height: self.frame.height)
    for (index, photo) in photos.enumerated() {
      let imageView = UIImageView(frame: CGRect(x:self.frame.width * CGFloat(index), y:0, width:self.frame.width, height:self.frame.height))
      imageView.contentMode = .scaleAspectFill
      imageView.sd_setImage(with: photo.url, placeholderImage: #imageLiteral(resourceName: "banana"))
      self.addSubview(imageView)
    }
    self.isPopulated = true
  }
}
