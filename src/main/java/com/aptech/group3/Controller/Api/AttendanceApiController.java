package com.aptech.group3.Controller.Api;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.Dto.AttendanceCreateDto;
import com.aptech.group3.Dto.AttendanceDto;
import com.aptech.group3.service.AttendanceService;

@RestController
@RequestMapping({"/api/attendance"})
public class AttendanceApiController {

	@Autowired AttendanceService attendanceService;
	
	@PostMapping("/create/{id}")
	public void create(  @PathVariable(name = "id") Long lessonId, @RequestBody AttendanceCreateDto dto)
	{
		attendanceService.create(dto.getStudentId(), lessonId, dto.getStatus());
	}
	
	@GetMapping("/test")
	public List<AttendanceDto> test(){
		Long classId =(long) 22;
		Date day= new Date();
		List<AttendanceDto> data= attendanceService.getListAttendanceDto(classId, day);	
		return data;
		
	}
		
	
}



