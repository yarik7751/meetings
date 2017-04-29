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
  var time: Date!
  var man: String!
  var selectedWoman: String?
  var women: [String]?
  var present: Double!
  var preferredAge: Int?
  var preferredHeight: Double?
  var preferredHairColor: String?
  var state:MeetingState!
  var id: Int!
  
  required init(place: String, time: Date, man: String, present: Double) {
    self.place = place
    self.time = time
    self.man = man
    self.present = present
    self.state = .pending
  }
  
  static func createFakeMeetings() -> [Meeting] {
    let first = Meeting(place: "Restaurant", time: Date().addingTimeInterval(19000), man: "Igor", present: 100)
    let second = Meeting(place: "Cinema", time: Date().addingTimeInterval(6000), man: "Maxim", present: 200)
    let third = Meeting(place: "Bedroom", time: Date().addingTimeInterval(3600), man: "Andrew", present: 50)
    third.state = .scheduled
    return [first, second, third]
  }
  
  static func getScheduled(from meetings: [Meeting]) -> [Meeting] {
    var result = [Meeting]()
    for meeting in meetings {
      if meeting.state == .scheduled {
          result.append(meeting)
      }
    }
    return result
  }
  
}


enum MeetingState: String {
  case scheduled = "MEETING_STATE_Scheduled"
  case pending = "MEETING_STATE_Pending"
}
