package com.aptech.group3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.entity.StudentClass;

@Service
public interface StudentClassService {
	public List<StudentClass> findSubjectByStudentId(Long studentId);
	public List<StudentClass> findEarliestByStatus(String status);
	public void RegisterClass(ClassForSubjectDto dto, Long userId);
	
}
