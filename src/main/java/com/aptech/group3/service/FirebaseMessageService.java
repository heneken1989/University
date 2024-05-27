package com.aptech.group3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.model.NotificationMessage;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

@Service
public class FirebaseMessageService {

	@Autowired 
	private FirebaseMessaging firebaseMessaging;
	
	
	public String sendNotificationByToken(NotificationMessage notificationMessage) {
		
		Notification notification = Notification.builder()
				.setTitle(notificationMessage.getTitle())
				.setBody(notificationMessage.getBody())
				.setImage(notificationMessage.getImage())
				.build();
		
		Message message= Message.builder()
				.setToken(notificationMessage.getRecripientToken())
				.setNotification(notification)
				.putAllData(notificationMessage.getData())
				.build();
		
		try {
			firebaseMessaging.send(message);
			return "Success send ing notification";
		}catch(FirebaseMessagingException e) {
			return "error exception occur";
		}
	}
}
