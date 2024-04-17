package com.aptech.group3.Controller.Api;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.Holiday;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.service.HolidayService;
import com.aptech.group3.service.SemesterService;

import shared.BaseMethod;

@RestController
@RequestMapping({ "/api/class" })
public class ClassSubjectApi {

	@Autowired
	HolidayService holidayService;

	@Autowired
	SemesterService semeterService;

	@GetMapping("/dateend")
	public Date getDateEnd(@RequestParam(name = "start") String start, @RequestParam(name = "type") String type) {

		Date startDate = BaseMethod.convertDate(start);
		
	
		Semeter currentSemester = semeterService.getCurrentSemester();

		List<Holiday> listHoliday = holidayService.getHolidayByYear(currentSemester.getYear());


		Calendar endate =  Calendar.getInstance();
		endate.setTime(startDate);
		if (type.equals("all")) {
			endate.add(Calendar.DATE, 16*7+1);
		}

		if (type.equals("fhalf") || type.equals("lhalf")) {
			endate.add(Calendar.DATE, 8*7+1);
		}

		
		
		  listHoliday.forEach(e -> { int cws =
		  BaseMethod.customCompareDate(e.getDate(), startDate); int cwe =
		  BaseMethod.customCompareDate(e.getDate(), endate.getTime());
		  
		  if (cws >= 0 && cwe <= 0 && BaseMethod.getWeekDay(startDate) ==
		  BaseMethod.getWeekDay(e.getDate())) { 
				endate.add(Calendar.DATE, 7);
		  }
		  });
		 
		return endate.getTime();
	}
}
