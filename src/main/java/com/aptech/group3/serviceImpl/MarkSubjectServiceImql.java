package com.aptech.group3.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.MarkSubjectDto;
import com.aptech.group3.Repository.MarkSubjectRepository;
import com.aptech.group3.entity.MarkSubject;
import com.aptech.group3.service.MarkSubjectService;

@Service
public class MarkSubjectServiceImql implements MarkSubjectService  {
	
	
	@Autowired private MarkSubjectRepository repo;

	    
	    public List<MarkSubject> getMarksByStudentId(Long studentId) {
	        return repo.findByUserId(studentId);
	    }	
}
