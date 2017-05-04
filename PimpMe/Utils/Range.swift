//
//  Range.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 04.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

protocol Range {
  var start: Double {get set}
  var end: Double {get set}
}

struct Age:Range {
  var start: Double
  var end: Double
}

struct Height: Range {
  var start: Double
  var end: Double
}

struct Time: Range {
  var start: Double
  var end: Double
}


extension Double {
  func isIn(range: Range)-> Bool {
    return self > range.start && self < range.end ? true : false
  }
}
