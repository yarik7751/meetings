//
//  ChatViewController.swift
//  PimpMe
//
//  Created by Игорь Майсюк on 05.05.17.
//  Copyright © 2017 ElateSoftware. All rights reserved.
//

import UIKit
import FirebaseDatabase
import FirebaseAuth
import JSQMessagesViewController

class ChatViewController: JSQMessagesViewController {
  var messages = [JSQMessage]()
  lazy var outgoingBubbleImageView: JSQMessagesBubbleImage = self.setupOutgoingBubble()
  lazy var incomingBubbleImageView: JSQMessagesBubbleImage = self.setupIncomingBubble()
  
  private lazy var database: FIRDatabaseReference = FIRDatabase.database().reference()
  private lazy var chatRef: FIRDatabaseReference = self.database.child("0") //TODO: - Change to Meeting ID
  private lazy var messagesRef: FIRDatabaseReference = self.chatRef.child("messages")
  private var newMessageRefHandle: FIRDatabaseHandle?
  
  override func viewDidLoad() {
    super.viewDidLoad()
    FIRAuth.auth()?.signInAnonymously(completion: nil)
    self.senderId = "0" //TODO: - Change to actual senderID
    self.senderDisplayName = User.name
    
    // No avatars
    collectionView!.collectionViewLayout.incomingAvatarViewSize = CGSize.zero
    collectionView!.collectionViewLayout.outgoingAvatarViewSize = CGSize.zero
    observeMessages()
  }
  
  override func viewDidAppear(_ animated: Bool) {
    super.viewDidAppear(animated)
    finishReceivingMessage()
    observeTyping()
  }
  
  //MARK: - Messaging 
  private func addMessage(withId id: String, name: String, text: String) {
    if let message = JSQMessage(senderId: id, displayName: name, text: text) {
      messages.append(message)
    }
  }
  
  override func didPressSend(_ button: UIButton!, withMessageText text: String!, senderId: String!, senderDisplayName: String!, date: Date!) {
    let messageRef = messagesRef.childByAutoId()
    let messageItem = [
      "senderId": Int(senderId) ?? 0,
      "date": Int(Date().timeIntervalSince1970),
      "text": text!,
      ] as [String : Any]
    
    messageRef.setValue(messageItem)
    
    JSQSystemSoundPlayer.jsq_playMessageSentSound()
    
    finishSendingMessage()
    isTyping = false
  }
  
  private func observeMessages() {

    let messageQuery = messagesRef.queryLimited(toLast:25)
    
    newMessageRefHandle = messageQuery.observe(.childAdded, with: { (snapshot) -> Void in
    guard let messageData = snapshot.value as? NSDictionary  else { self.finishSendingMessage()
      return }
      self.addMessage(withId: "\(messageData["senderId"] as! Int)", name: "\(messageData["date"] as! Int)", text: messageData["text"] as! String)
      self.finishReceivingMessage()
    })
  }
  
  //MARK: - Typing indicator
  override func textViewDidChange(_ textView: UITextView) {
    super.textViewDidChange(textView)
    isTyping = textView.text != ""
  }
  
  private lazy var userIsTypingRef: FIRDatabaseReference = self.chatRef.child("typingIndicator").child(self.senderId)
  private var localTyping = false
  
  var isTyping: Bool {
    get {
      return localTyping
    }
    set {
      localTyping = newValue
      userIsTypingRef.setValue(newValue)
    }
  }
  
  private lazy var usersTypingQuery: FIRDatabaseQuery = self.chatRef.child("typingIndicator").queryOrderedByValue().queryEqual(toValue: true)
  
  private func observeTyping() {
    let typingIndicatorRef = chatRef.child("typingIndicator")
    userIsTypingRef = typingIndicatorRef.child(senderId)
    userIsTypingRef.onDisconnectRemoveValue()
    
    usersTypingQuery.observe(.value) { (data: FIRDataSnapshot) in
      if data.childrenCount == 1 && self.isTyping {
        return
      }
      
      self.showTypingIndicator = data.childrenCount > 0
      self.scrollToBottom(animated: true)
    }
  }
  
  //MARK: - Collection Setup

  override func collectionView(_ collectionView: JSQMessagesCollectionView!, messageDataForItemAt indexPath: IndexPath!) -> JSQMessageData! {
    return messages[indexPath.item]
  }
  
  override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    return messages.count
  }
  
  private func setupOutgoingBubble() -> JSQMessagesBubbleImage {
    let bubbleImageFactory = JSQMessagesBubbleImageFactory()
    return bubbleImageFactory!.outgoingMessagesBubbleImage(with: UIColor.jsq_messageBubbleBlue())
  }
  
  private func setupIncomingBubble() -> JSQMessagesBubbleImage {
    let bubbleImageFactory = JSQMessagesBubbleImageFactory()
    return bubbleImageFactory!.incomingMessagesBubbleImage(with: UIColor.jsq_messageBubbleLightGray())
  }
  
  override func collectionView(_ collectionView: JSQMessagesCollectionView!, messageBubbleImageDataForItemAt indexPath: IndexPath!) -> JSQMessageBubbleImageDataSource! {
    let message = messages[indexPath.item]
    return message.senderId == senderId ? outgoingBubbleImageView : incomingBubbleImageView
  }
  
  override func collectionView(_ collectionView: JSQMessagesCollectionView!, avatarImageDataForItemAt indexPath: IndexPath!) -> JSQMessageAvatarImageDataSource! {
    return nil
  }
  
  override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
    let cell = super.collectionView(collectionView, cellForItemAt: indexPath) as! JSQMessagesCollectionViewCell
    let message = messages[indexPath.item]
    cell.textView.textColor = message.senderId == senderId ? UIColor.white : UIColor.black
    return cell
  }
}
