//
//  File.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 04.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation

protocol Schedulable {
  var meetings: [Meeting] {get set}
  var scheduledMeetings: [Meeting] {get set}
  var hasScheduled: Bool {get set}
}

extension Schedulable {  
  func getMeeting(forIndexPath indexPath: IndexPath) -> Meeting {
    if hasScheduled {
      return indexPath.section == 0 ? scheduledMeetings[indexPath.row] : meetings[indexPath.row]
    } else {
      return meetings[indexPath.row]
    }
  }
  
  func getNumberOfRows(forSection section: Int) -> Int {
    guard hasScheduled else {return meetings.count }
    return section == 0 ? scheduledMeetings.count : meetings.count
  }
  
  func getTitle(forSection section: Int) -> String {
    guard hasScheduled else { return NSLocalizedString(MeetingState.pending.rawValue, comment: "") }
    return section == 0 ? NSLocalizedString(MeetingState.scheduled.rawValue, comment: ""): NSLocalizedString(MeetingState.pending.rawValue, comment: "")
  }
}
