//
//  Woman.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 02.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

class Woman  {
  var mainPhoto: Photo!
  var photos: [Photo]?
  var age: Int!
  var name: String!
  var height: Int!
  var hairColor: String!
  var id: Int?
  
  init(name: String, age: Int, height: Int, hairColor: String) {
    self.name = name
    self.age = age
    self.height = height
    self.hairColor = hairColor
  }
}
