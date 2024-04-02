package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.Dto.DiscussRoomDto;
import com.aptech.group3.entity.DiscussRoom;

public interface DiscussRoomService {
	public void create(DiscussRoomDto dto);

	public List<DiscussRoom> getList(Long id);

	public DiscussRoom getById(int id);

	public boolean checkTeacherInRoom(Long id, Long teacherId);

	public DiscussRoomDto getRoomDto(Long id);
}
