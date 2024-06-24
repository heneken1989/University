package com.aptech.group3.serviceImpl;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.SemesterEditDto;
import com.aptech.group3.Dto.SemeterDto;
import com.aptech.group3.Dto.TimeTableDto;
import com.aptech.group3.Repository.SemesterRepository;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.service.SemesterService;

import shared.BaseMethod;

@Service
public class SemesterServiceImpl implements SemesterService {

	@Autowired
	SemesterRepository sr;
	
	public Semeter getByYearAndName(int year,int name) {
		Semeter data= sr.findByYearAndName(year, name);
		return data;
	}
	
	
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
		
		System.out.print("SemesterId"+SemesterId);
		List<TimeTableDto> data= new ArrayList<>();
	
		Semeter currentSemster= sr.findById(SemesterId);
		

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






	//du 
	
	private Date adjustToMonday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek != Calendar.MONDAY) {
			int daysToAdd = (Calendar.MONDAY - dayOfWeek + 7) % 7;
			daysToAdd = (daysToAdd == 0) ? 7 : daysToAdd; // if it's already Monday, move to the next Monday
			calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
		}

		return calendar.getTime();
	}
	
	
		public Semeter create(SemeterDto dto) {
		Semeter sm = new Semeter();
		/* Date newsem = dto.getDaystart(); */
		Date newsem = dto.getDaystart();
		
		 // Check if the day_start is a Monday
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(newsem);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != Calendar.MONDAY) {
            throw new IllegalArgumentException("day_start must be a Monday");
        }

		Date endsem = dto.getDayend();

		 
		Date daystart = new Date();
		daystart.setTime(newsem.getTime() - 14 * 86400000);

		Date closedate = new Date();
		closedate.setTime(newsem.getTime() - 2 * 86400000);

		sm.setName(dto.getName());
		sm.setYear(BaseMethod.toCalendar(dto.getDaystart()).get(Calendar.YEAR));
		/* sm.setStartRegisDate(dto.getStartRegisDate()); */
		sm.setStartRegisDate(daystart);

		sm.setCloseRegisDate(closedate);
		sm.setDay_start(dto.getDaystart());
		sm.setDay_end(dto.getDayend());

		Semeter newsm = sr.save(sm);
		return newsm;
	}
	

	public void updateSemester(SemesterEditDto dto) {
		Optional<Semeter> smUpdate = sr.findById(dto.getId());
		

		if (smUpdate.isPresent()) {
			
			
			
			Date newsem = dto.getDay_start();

			 // Check if the day_start is a Monday
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(newsem);
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
	        if (dayOfWeek != Calendar.MONDAY) {
	            throw new IllegalArgumentException("day_start must be a Monday");
	        }
	        
			Date endsem = dto.getDay_end();

			Date daystart = new Date();
			daystart.setTime(newsem.getTime() - 14 * 86400000);

			Date closedate = new Date();
			closedate.setTime(newsem.getTime() - 2 * 86400000);
			Semeter sm = smUpdate.get();
			sm.setName(dto.getName());
			sm.setStartRegisDate(daystart);

			sm.setCloseRegisDate(closedate);
			sm.setDay_start(dto.getDay_start());
			sm.setDay_end(dto.getDay_end());
			sm.setYear(BaseMethod.toCalendar(dto.getDay_start()).get(Calendar.YEAR));
			
			sr.save(sm);
		}
	}
	
}
