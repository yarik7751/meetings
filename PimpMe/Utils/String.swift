//
//  String.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 28.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

extension String {
  
  var isValidEmail: Bool {
    let emailRegex = ".+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2}[A-Za-z]*"
    let emailTest = NSPredicate(format: "SELF MATCHES %@", emailRegex)
    return emailTest.evaluate(with: self, substitutionVariables: nil)
  }
  
  var hasText: Bool {
    return self.characters.count > 0 ? true : false
  }
  
}
