package com.aptech.group3.serviceImpl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.DiscussRoomDto;
import com.aptech.group3.Dto.DiscussRoomEditDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.DiscussRoomRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.DiscussRoom;
import com.aptech.group3.service.DiscussRoomService;

import shared.BaseMethod;

@Service
public class DiscussRoomServiceImpl implements DiscussRoomService {

	@Autowired
	DiscussRoomRepository repo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	ClassForSubjectRepository classRepo;

	@Autowired
	private ModelMapper mapper;

	public Long edit(DiscussRoomEditDto dto, Long id) {
		DiscussRoom data = repo.findById(id).orElse(null);
		data.setName(dto.getName());
		data.setTopic(dto.getTopic());
		if (dto.getType() != null) {
			Date date= BaseMethod.convertDateFull(dto.getExpried_text());
			if ("minute".equalsIgnoreCase(dto.getType())) {
				date.setTime(data.getExpried().getTime() + dto.getExpried_date() * 60000);
			} else if ("hour".equalsIgnoreCase(dto.getType())) {
				date.setTime(data.getExpried().getTime() +  dto.getExpried_date() * 3600000);
			} else if ("day".equalsIgnoreCase(dto.getType())) {
				date.setTime(data.getExpried().getTime() +  dto.getExpried_date() * 86400000);
			}

			data.setExpried(date);
		}

		repo.save(data);
		return data.getOwner().getId();

	}

	public DiscussRoom getById(int id) {
		return repo.findById(id);
	}

	public DiscussRoomDto getRoomDto(Long id) {
		DiscussRoomDto data = mapper.map(repo.findById(id), DiscussRoomDto.class);
		return data;

	}

	public DiscussRoomEditDto createEditDto(Long id) {
		DiscussRoomEditDto data = repo.findById(id).map(entity -> {
			DiscussRoomEditDto result = mapper.map(entity, DiscussRoomEditDto.class);
			result.setExpried_text(entity.getExpried().toString());
			return result;
		}).orElse(null);

		return data;

	}

	public boolean checkTeacherInRoom(Long id, Long teacherId) {
		DiscussRoom check = repo.findByIdAndTeacherId(id, teacherId);
		if (check == null) {
			return false;
		}
		return true;
	}

	public void create(DiscussRoomDto dto) {
		DiscussRoom sclass = mapper.map(dto, DiscussRoom.class);

		Date date = new Date();

		if ("minute".equalsIgnoreCase(dto.getType())) {
			date.setTime(date.getTime() + dto.getExpried_date() * 60000);
		} else if ("hour".equalsIgnoreCase(dto.getType())) {
			date.setTime(date.getTime() + dto.getExpried_date() * 3600000);
		} else if ("day".equalsIgnoreCase(dto.getType())) {
			date.setTime(date.getTime() + dto.getExpried_date() * 86400000);
		}

		sclass.setExpried(date);
		userRepo.findById(dto.getTeacher_id()).ifPresent(sclass::setTeacher);
		classRepo.findById(dto.getClass_id()).ifPresent(sclass::setOwner);

		repo.save(sclass);

	}

	public List<DiscussRoom> getList(Long id) {
		return repo.findByOwnerId(id);
	}
}