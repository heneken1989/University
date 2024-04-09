package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.entity.QuizExam;

public interface QuizExamService {
	public QuizExam findExamByStudentIdAndQuizId(Long id ,Long quizId);
	public List<QuizExam> findExamByStudentIdAndClassId(Long studentId ,Long classId);
}
