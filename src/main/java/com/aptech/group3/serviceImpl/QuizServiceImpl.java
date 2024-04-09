package com.aptech.group3.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aptech.group3.Dto.QuizCreateDto;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.service.QuizService;

import shared.BaseMethod;

@Service
public class QuizServiceImpl implements QuizService {
               
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private SubjectRepository subjectRepository;
	
	@Autowired
	ModelMapper mapper;
	
	    @Transactional
	    public Quiz create(QuizCreateDto dto) {
	        try {
	
	            
	            Quiz quiz = mapper.map(dto, Quiz.class);
	            subjectRepository.findById(dto.getSubject_id()).ifPresent(quiz::setSubject);
	            return quizRepository.save(quiz); 
	       
	        } catch (Exception ex) {
	 
	            throw ex;
	        }
	    }
}
