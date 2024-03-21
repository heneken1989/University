package com.aptech.group3.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.entity.ClassForSubject;




@Service
public class ClassForSubjectService {
	
	   @Autowired
	    private ClassForSubjectRepository classRepository;
	   
	   @Autowired
	    private ModelMapper mapper;
	   
	   public List<ClassForSubjectDto> findById(Long id)
	   {
		   List<ClassForSubject> classeSubjecs = classRepository.findBySubjectId(id);
		   return mapper.map(classeSubjecs, new TypeToken<List<ClassForSubjectDto>>() {}.getType());
	
	   }
}
