//
//  Man.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 02.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

class Man {
  var mainPhoto: Photo?
  var photos: [Photo]?
  let name: String!
  var age: Int?
  var about: String?
  var id: Int?

  init(name: String) {
    self.name = name
  }
}
