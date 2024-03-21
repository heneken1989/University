package com.aptech.group3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;





@Service
public class StudentClassService {
	
	@Autowired
	private StudentClassRepository repo ;
	
	public List<StudentClass> findSubjectByStudentId(Long studentId)
	{
		return repo.findByStudentId(studentId);
	}
	


}
