package com.aptech.group3.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.QuizCreateDto;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.RequiredSubject;

@Service
public interface RequiredSubjectService {
	public List<RequiredSubject> findListRequiredSubjectBySubjectId(Long subjectId);
}