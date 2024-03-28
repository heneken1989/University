package com.aptech.group3.serviceImpl;


import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.SemesterRepository;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.service.SemesterService;

@Service
public class SemesterServiceImpl implements SemesterService {

	@Autowired
	SemesterRepository sr;
	
	public Semeter getCurrentSemester(){
		int currentYear= Calendar.getInstance().get(Calendar.YEAR);
		int currentMonth= Calendar.getInstance().get(Calendar.MONTH);
		Semeter data=  sr.currentSemester(currentYear,currentMonth >6 ?2 :1);
		return data;
	}
	
	public List<Semeter> findAll() {
		List<Semeter> data=sr.findAll();
		return data;
		
	}
}
