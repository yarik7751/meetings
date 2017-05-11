//
//  PlacePicker.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 03.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import UIKit
import GooglePlacePicker

class PlacePicker: NSObject {
  private let config = GMSPlacePickerConfig(viewport: nil)
  private let picker: GMSPlacePicker!
  weak var delegate: PlacePickerDelegate?

   override init() {
    picker = GMSPlacePicker(config: config)
    super.init()
  }

  func pickPlace() {
    picker.pickPlace(callback: { place, _ in
      guard let place = place else {
        self.delegate?.placePicker(didFinishPicking: "N/A", address: "N/A")
        return}
      self.delegate?.placePicker(didFinishPicking: place.name, address: place.formattedAddress ?? "")
    })
  }
}

protocol PlacePickerDelegate: class {
  func placePicker(didFinishPicking place: String, address: String)
}
