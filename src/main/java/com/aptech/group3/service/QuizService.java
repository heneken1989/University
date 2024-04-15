package com.aptech.group3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.QuizCreateDto;
import com.aptech.group3.entity.Quiz;

@Service
public interface QuizService {
	  public Quiz create(QuizCreateDto dto ,Long teacherId);
	  public List<Quiz> findListQuizBySubjectIdAndTeacherId(Long subjectId,Long teacherId);
}
