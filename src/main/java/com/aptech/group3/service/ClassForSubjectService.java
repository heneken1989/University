package com.aptech.group3.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.ClassSubjectEditOneDto;
import com.aptech.group3.Dto.TimeTableShowDto;
import com.aptech.group3.entity.ClassForSubject;

public interface ClassForSubjectService {
	//THanh
	public List<ClassForSubject> getClassesForToday(Long teacherId);
	
	public List<ClassForSubject> getByTeacherIdAndSemester(Long teacherId, Long semesterId);
	
	public List<TimeTableShowDto> getTeacherTimeTable(Long teacherId,Long semesterId, Date dateStart, Date dateEnd);
	
	//new 
	public List<ClassForSubject> getClassSubjects(Long classId);
	public List<ClassForSubject> findAll();
	//public List<ClassForSubject> findBySemesterId(long id);
	public List<ClassForSubject> findBySemesterIdAndFieldId(Long semesterId,Long fieldId);
	public List<ClassForSubject> listClassBySemesterId(Long semesterId);
	
	
	public void Edit(ClassSubjectEditOneDto dto);
	public List<ClassForSubject> getAllByfieldAndSemester(Long semesterId, Long fieldId);
	
	public ClassSubjectEditOneDto getEditDto(Long id);
	
	public boolean checkType(Long id);
	
	

    
	public List<ClassForSubjectDto> findBySubjectIdAndDate(Long id,Date date);
	
	public List<ClassForSubject> findByRegistrationDate(Date date);

	
	public ClassForSubject findById(Long id);

	public void create(ClassSubjectCreateDto data);

	public void createAll(ClassSubjectAllDto data);
	
	
	public ClassForSubject findByClassId(int id);

	
	
	public List<ClassForSubjectDto> findBySubjectId(Long id);

		
	


	public Page<ClassForSubject> getSubjectByFieldAndSemester(int fieldId, int semesterId,Integer subjectId,Pageable pageable);
	
    // HIEN
	public List<ClassForSubject> findByTeacherId(Long teacherID);



	
	
}
