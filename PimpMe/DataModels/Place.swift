//
//  Place.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 11.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

class Place {
  var name: String!
  var latitude: Double!
  var longitude: Double!

  init(name: String = NSLocalizedString("PLACE_NoPlace", comment: ""), latitude: Double, longitude: Double) {
    self.name = name
    self.latitude = latitude
    self.longitude = longitude
  }

}
