package com.aptech.group3.serviceImpl;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.FieldDto;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.RequiredSubjectRepository;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.service.FiledService;
import com.aptech.group3.service.RequiredSubjectService;

@Service
public class RequiredSubjectServiceImpl implements RequiredSubjectService {

    @Autowired
    private RequiredSubjectRepository requiredSubjectRepository;
    
    public List<RequiredSubject> findListRequiredSubjectBySubjectId(Long subjectId)
    {
    	return requiredSubjectRepository.findBySubjectId(subjectId);
    }


   
}