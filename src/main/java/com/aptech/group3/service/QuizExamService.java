package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.entity.QuizExam;

public interface QuizExamService {
	public QuizExam findExamByStudentId(Long id ,Long quizId);
}
