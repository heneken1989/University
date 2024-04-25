package com.aptech.group3.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.RoomRegistedRepository;
import com.aptech.group3.Repository.RoomRepository;
import com.aptech.group3.entity.RoomRegisted;
import com.aptech.group3.service.RoomRegistedService;

@Service
public class RoomRegistedServiceImpl implements RoomRegistedService {
	
	@Autowired RoomRegistedRepository repo;
	
	@Autowired RoomRepository roomRepo;
	public void update(Long classId,Long roomId) {
		
		System.out.print("in update functtion");
		RoomRegisted data=repo.findByClass_registedId(classId);
		roomRepo.findById(roomId).ifPresent(data::setRoom);
		repo.save(data);
	}
}
