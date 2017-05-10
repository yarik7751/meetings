//
//  Meeting.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

class Meeting {
  
  var place: String!
  var time: Time!
  var man: Man!
  var selectedWoman: Woman?
  var women: [Woman]?
  var present: Double!
  var preferredAge: Age!
  var preferredHeight: Height!
  var preferredHairColor: String?
  var state:MeetingState
  var id: Int!
  
  required init(place: String, time: Time, man: Man, present: Double) {
    self.place = place
    self.time = time
    self.man = man
    self.present = present
    self.state = .pending
    id = 0 //TODO: - Change for id getting from API
  }
  
  func cancel() {
    MeetingsStorage.shared.cancel(self)
  }
  
  func createWoman() {
    let woman = Woman(name: "Даша", age: 23, height: 178, hairColor: "Синий")
    let photo = Photo(id: 0, url: URL(string: "https://cdn.pixabay.com/photo/2017/04/08/10/23/surfer-2212948_960_720.jpg")!)
    woman.photos = [photo, photo, photo]
    self.women = [woman]
  }
  
}

extension Meeting: Equatable {
  static func ==(lhs: Meeting, rhs: Meeting) -> Bool {
    guard lhs.place == rhs.place && lhs.time.start == rhs.time.start && lhs.id == rhs.id else {return false}
    return true
  }
}


enum MeetingState: String {
  case scheduled = "MEETING_STATE_Scheduled"
  case pending = "MEETING_STATE_Pending"
}
