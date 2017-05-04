//
//  MeetingsStorage.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 04.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

protocol MeetingsStorageDelegate: class {
  func meetingStorageUpdated()
}

class MeetingsStorage {
  
  private static let sharedInstance = MeetingsStorage()
  static var shared: MeetingsStorage {
    return sharedInstance
  }
  weak var delegate: MeetingsStorageDelegate?
  private init() {
    createFakeMeetings() //TODO: - Get rid of fake meetings
  }
  
  private var meetings = [Meeting]()
  private var scheduledMeetings = [Meeting]()
  
  func add(_ meeting: Meeting) {
    meetings.append(meeting)
    delegate?.meetingStorageUpdated()
  }
  
  func remove(_ meeting: Meeting) {
    guard let index = meetings.index(of: meeting) else {return}
    meetings.remove(at: index)
    delegate?.meetingStorageUpdated()
  }
  
  func getMeetings() -> [Meeting] {
    return meetings
  }
  
  func getScheduledMeetings() -> [Meeting] {
    for meeting in meetings {
      if meeting.state == .scheduled {
        scheduledMeetings.append(meeting)
        meetings.remove(at: meetings.index(of: meeting)!)
      }
    }
    return scheduledMeetings
  }
  
  private func createFakeMeetings(){
    guard meetings.count == 0 else {return}
    let first = Meeting(place: "Restaurant", time: Time(start: Date().timeIntervalSince1970, end: Date(timeIntervalSinceNow: 19000).timeIntervalSince1970), man: Man(name: "Igor"), present: 100)
    let second = Meeting(place: "Cinema", time: Time(start: Date().timeIntervalSince1970, end: Date(timeIntervalSinceNow: 12000).timeIntervalSince1970), man: Man(name:"Maxim"), present: 200)
    let third = Meeting(place: "Bedroom", time: Time(start: Date().timeIntervalSince1970, end: Date(timeIntervalSinceNow: 15000).timeIntervalSince1970), man: Man(name:"Andrew"), present: 50)
    third.state = .scheduled
    meetings.append(first)
    meetings.append(second)
    meetings.append(third)
    delegate?.meetingStorageUpdated()
  }
}
