//
//  UIButton.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 03.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import UIKit

class RotationButton: UIButton {
  
  enum ButtonState {
    case expanded
    case collapsed
  }
  
  var buttonState:ButtonState = ButtonState.collapsed {
    didSet {
      switch buttonState {
      case .expanded:
        let transform = CGAffineTransform(rotationAngle: .pi/2)
        self.transform = transform
      default:
        self.transform = CGAffineTransform.identity
      }
    }
  }
  
  func switchState() {
    self.buttonState = self.buttonState == .collapsed ? .expanded : .collapsed
  }
  
  
}
