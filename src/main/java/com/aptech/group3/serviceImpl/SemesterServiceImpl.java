package com.aptech.group3.serviceImpl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.TimeTableDto;
import com.aptech.group3.Repository.SemesterRepository;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.service.SemesterService;

import shared.BaseMethod;

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
	
	public Semeter getSemesterById(int id) {
		return sr.findById(id);
	} 
	
	public List<TimeTableDto> getListWeek(int SemesterId){
		List<TimeTableDto> data= new ArrayList<>();
	
		Semeter currentSemster= sr.findById(SemesterId);
		
		System.out.print(currentSemster);

		Calendar cal = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		cal.setTime(currentSemster.getDay_start());
		int startWeek = cal.get(Calendar.WEEK_OF_YEAR);
		
		cal2.setTime(currentSemster.getDay_end());
		int endWeek = cal2.get(Calendar.WEEK_OF_YEAR);
		
		int check=1;
		for(int i=startWeek;i<=endWeek;i++ ) {
			
			if(check==1) {
				Date endDate= new Date();
				Date startDate=currentSemster.getDay_start();
				endDate.setTime(startDate.getTime() +  6 * 86400000);
				data.add(new TimeTableDto(check,startDate ,endDate));
				check++;
			}else {
				TimeTableDto before=data.get(check-2);
				
				
				Date startDate= new Date();
				Date oldstart=before.getEnd_day();
				startDate.setTime(oldstart.getTime() + 86400000);
				Date endDate= new Date();
				endDate.setTime(startDate.getTime() +6* 86400000);
				
				data.add(new TimeTableDto(check,startDate,endDate));
				check++;
			}
			
		}
		return data;
		
	}
	
	
}
