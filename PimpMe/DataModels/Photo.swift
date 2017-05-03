//
//  Photo.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 02.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation


struct Photo {
  let id: Int!
  let url: URL!
  
  init(id: Int, url: URL) {
    self.id = id
    self.url = url
  }
}
