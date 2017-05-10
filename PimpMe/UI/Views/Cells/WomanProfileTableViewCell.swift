//
//  WomanProfileTableViewCell.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 10.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit

class WomanProfileTableViewCell: UITableViewCell {

  @IBOutlet weak var photoScrollView: PhotoScrollView!
  @IBOutlet weak var pageControl: UIPageControl!
  @IBOutlet weak var nameLabel: UILabel!
  
  var woman: Woman! {
    didSet {
      photoScrollView.delegate = self
      pageControl.numberOfPages = woman.photos!.count
      nameLabel.text = woman.name
    }
  }

  override func layoutSubviews() {
    super.layoutSubviews()
    contentView.layoutSubviews()
    photoScrollView.populate(with: woman.photos!)
  }
}

extension WomanProfileTableViewCell: UIScrollViewDelegate {
  func scrollViewDidEndDecelerating(_ scrollView: UIScrollView){
    let pageWidth = scrollView.frame.width
    let currentPage = floor((scrollView.contentOffset.x - pageWidth / 2) / pageWidth) + 1
    
    self.pageControl.currentPage = Int(currentPage)
  }
}
