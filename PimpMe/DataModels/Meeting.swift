//
//  Meeting.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 29.04.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

class Meeting {

  var place: Place!
  var time: Time!
  var man: Man!
  var selectedWoman: Woman?
  var women: [Woman]?
  var present: Double!
  var preferredAge: Age!
  var preferredHeight: Height!
  var preferredHairColor: String?
  var state: MeetingState
  var id: Int!

  private init(place: Place, time: Time, man: Man, present: Double) {
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

  // MARK: - Fabric Method

  static func createMeeting(at place: String, start: Date, end: Date, mansName: String, present: Double) -> Meeting {
    let meeting = Meeting(place: createPlace(with: place), time: createTime(start, end: end), man: createMan(with: mansName), present: present)
    meeting.createWoman()
    return meeting
  }

  private static func createPlace(with name: String) -> Place {
    return Place(name: name, latitude: 53.909704, longitude: 27.512522)
  }

  private static func createTime(_ start: Date, end: Date) -> Time {
    return Time(start: start.timeIntervalSince1970,
         end: end.timeIntervalSince1970)
  }

  private static func createMan(with name: String) -> Man {
    return Man(name: name)
  }

  private func createWoman() {
    let woman = Woman(name: "Даша", age: 23, height: 178, hairColor: "Синий")
    let photo = Photo(id: 0,
                      url: URL(string: "https://cdn.pixabay.com/photo/2017/04/08/10/23/surfer-2212948_960_720.jpg")!)
    woman.photos = [photo, photo, photo]
    self.women = [woman]
  }

}

extension Meeting: Equatable {
  static func == (lhs: Meeting, rhs: Meeting) -> Bool {
    guard lhs.place.name == rhs.place.name && lhs.time.start == rhs.time.start && lhs.id == rhs.id else {return false}
    return true
  }
}

enum MeetingState: String {
  case scheduled = "MEETING_STATE_Scheduled"
  case pending = "MEETING_STATE_Pending"
}
