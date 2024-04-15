package com.aptech.group3.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.TimeTableShowDto;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.entity.StudentClass;

@Service
public interface StudentClassService {
	
	public List<TimeTableShowDto> getCurrentTimeTable( Long studentId, Date dateStart,Date dateEnd, Long semesterId);
	// new method 
	
	
	
	public List<StudentClass> findSubjectByStudentId(Long studentId);
	
	public List<StudentClass> findEarliestByStatus(String status);
	
	public void RegisterClass(ClassForSubjectDto dto, Long userId);
	
	public boolean CheckStuentInClass(Long stuentId, Long ClassId);
	
	public List<StudentClass> findByClassForSubjectId(Long classId);
	
}