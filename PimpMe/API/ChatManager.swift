//
//  ChatManager.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 05.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import Foundation
import FirebaseDatabase

class ChatManager: NSObject {

  private lazy var database: FIRDatabaseReference = FIRDatabase.database().reference()
  private var chatRefHandle: FIRDatabaseHandle?

}
