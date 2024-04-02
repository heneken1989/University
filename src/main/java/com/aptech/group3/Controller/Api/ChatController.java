package com.aptech.group3.Controller.Api;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.aptech.group3.Dto.ChatMessage;
import com.aptech.group3.Dto.MessageType;
import com.aptech.group3.entity.DiscussRoom;
import com.aptech.group3.service.DiscussMessageService;
import com.aptech.group3.service.DiscussRoomService;

@Controller	
public class ChatController {
	
	@Autowired 
	DiscussMessageService service;
	
	@Autowired 
	DiscussRoomService roomService;

	@MessageMapping("/chat.sendMessage/{roomId}")
	@SendTo("/topic/public/{roomId}")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage, @DestinationVariable String roomId) {
		
		  DiscussRoom roomData=roomService.getById(Integer.parseInt(roomId));
		  
		  Date currenrDate= new Date();
		
		
		
		 if(currenrDate.after(roomData.getExpried())) {
		 chatMessage.setType(MessageType.OVER); return chatMessage; }
		 
		
		
		service.create(chatMessage);
	    return chatMessage;
	}

	@MessageMapping("/chat.addUser/{roomId}")
	@SendTo("/topic/public/{roomId}")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor, @DestinationVariable String roomId) {
	    // Add username in web socket session
	    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
	    return chatMessage;
	}
	}
