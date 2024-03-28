package com.aptech.group3.service;

import org.springframework.data.domain.Page;

import com.aptech.group3.entity.QuizQuestion;

public interface QuizQuestionService {
	 public Page<QuizQuestion> findPaginatedQuestions(int page, int pageSize);
	 public Page<QuizQuestion> findPaginatedQuestionsByQuizId(Long quizId, int page, int pageSize);
}
