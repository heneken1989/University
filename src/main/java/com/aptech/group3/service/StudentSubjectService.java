package com.aptech.group3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.dao.StudentSubjectRepository;
import com.aptech.group3.entity.StudentSubject;





@Service
public class StudentSubjectService {
	
	@Autowired
	private StudentSubjectRepository repo ;
	
	public List<StudentSubject> findSubjectByStudentId(Long studentId)
	{
		return repo.findByUserId(studentId);
	}

}
