package com.aptech.group3.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizExam;

public interface QuizExamRepository extends JpaRepository<QuizExam, Long> {
         
	public QuizExam findByStudentId(Long id);
}
