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

class PlacePicker:NSObject {
  private let config = GMSPlacePickerConfig(viewport: nil)
  private let picker: GMSPlacePicker!
  weak var delegate: PlacePickerDelegate?
  
   override init() {
    picker = GMSPlacePicker(config: config)
    super.init()
  }
  
  func pickPlace() {
    picker.pickPlace(callback: {
      place, error in
      guard place != nil else {return}
      self.delegate?.placePicker(didFinishPicking: place?.name ?? "", address: place?.formattedAddress ?? "")
      print("Name: \(place!.name)")
      print("Place address \(String(describing: place!.formattedAddress))")
      print("Place attributions \(String(describing: place!.attributions))")
    })
  }
}

protocol PlacePickerDelegate: class {
  func placePicker(didFinishPicking place: String, address: String)
}
