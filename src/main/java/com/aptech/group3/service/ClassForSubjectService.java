package com.aptech.group3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.dao.ClassForSubjectRepository;
import com.aptech.group3.entity.ClassForSubject;




@Service
public class ClassForSubjectService {
	
	   @Autowired
	    private ClassForSubjectRepository classRepository;
	   
	   public List<ClassForSubject> findById(Long id)
	   {
		  return classRepository.findBySubjectId(id);
	   }
}
