package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.Dto.ChatMessage;
import com.aptech.group3.Dto.DiscussMessageDto;

public interface DiscussMessageService {

	
	public void create(ChatMessage dto);
	 public List<DiscussMessageDto> getMessageByRoomId (Long id);
}
