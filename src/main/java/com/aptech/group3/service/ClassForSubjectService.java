package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;

import com.aptech.group3.entity.ClassForSubject;

public interface ClassForSubjectService {
	public List<ClassForSubject> findById(Long id);

	public void create(ClassSubjectCreateDto data);

	public void createAll(ClassSubjectAllDto data);
	
	
	public ClassForSubject findByClassId(int id);

	public Page<ClassForSubject> getSubjectByFieldAndSemester(int fieldId, int semesterId,Integer subjectId,Pageable pageable);
	
	
	// hien
	public List<ClassForSubjectDto> findBySubjectId(Long id);
	
	
}
