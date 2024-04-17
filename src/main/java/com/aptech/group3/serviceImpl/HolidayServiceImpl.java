package com.aptech.group3.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.HolidayCreateDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.HolidayRepository;
import com.aptech.group3.entity.Holiday;
import com.aptech.group3.service.HolidayService;

import shared.BaseMethod;

@Service
public class HolidayServiceImpl implements HolidayService {

	@Autowired HolidayRepository repo;
	@Autowired ClassForSubjectRepository classRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	public List<Holiday> getHolidayByYear(int year){
		return repo.findByYear(year);
	}
	
	public void create(HolidayCreateDto dto) {
		Holiday h = mapper.map(dto, Holiday.class);
		h.setDate(BaseMethod.convertDate(dto.getDate_cre()));
		if(dto.getClassId()!=null) {
		
		
		classRepo.findById(dto.getClassId()).ifPresent(h::setClassSubject);
	}
		repo.save(h);
	}
	
}
