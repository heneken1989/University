package com.aptech.group3.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.entity.QuizAnswer;

public interface QuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {

}