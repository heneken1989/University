package com.aptech.group3.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.service.QuizQuestionService;


@Service
public class QuizQuestionServiceImpl implements QuizQuestionService {

	@Autowired
    private QuizQuestionRepository quizQuestionRepository;

    public Page<QuizQuestion> findPaginatedQuestions(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return quizQuestionRepository.findAll(pageRequest);
    }
    
    // New method to find paginated questions by Quiz ID
    public Page<QuizQuestion> findPaginatedQuestionsByQuizId(Long quizId, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return quizQuestionRepository.findByQuizId(quizId, pageRequest);
    }
}
