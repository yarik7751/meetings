//
//  Date.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

extension Double {

  private static let dateFormatter: DateFormatter = {
      let formatter = DateFormatter()
      formatter.timeZone = TimeZone.current
      formatter.dateFormat = "dd.MM.yyyy, HH:mm"//"yyyy-MM-dd'T'HH:mm:ss"
      return formatter
    }()

    var stringValue: String {
      return Double.dateFormatter.string(from: Date(timeIntervalSince1970: self))
    }
}
