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

  func cancel(_ meeting: Meeting) {
    switch meeting.state {
    case .pending:
      guard let index = meetings.index(of: meeting) else {return}
      meetings.remove(at: index)
    default:
      guard let index = scheduledMeetings.index(of: meeting) else {return}
      scheduledMeetings.remove(at: index)
    }
    delegate?.meetingStorageUpdated()
  }

  func getMeetings() -> [Meeting] {
    return meetings
  }

  func getScheduledMeetings() -> [Meeting] {
    for meeting in meetings where meeting.state == .scheduled {
      scheduledMeetings.append(meeting)
      meetings.remove(at: meetings.index(of: meeting)!)
    }
    return scheduledMeetings
  }

  private func createFakeMeetings() {
    guard meetings.isEmpty else {return}
    let first = Meeting.createMeeting(at: "Restaurant", start: Date(), end: Date().addingTimeInterval(19000), mansName: "Igor", present: 100)
    let second = Meeting.createMeeting(at: "Cinema", start: Date(), end: Date().addingTimeInterval(14000), mansName: "Maxim", present: 200)
    let third = Meeting.createMeeting(at: "Bedroom", start: Date(), end: Date().addingTimeInterval(120), mansName: "Andrew", present: 50)
    third.state = .scheduled
    meetings.append(first)
    meetings.append(second)
    meetings.append(third)
    delegate?.meetingStorageUpdated()
  }

}
