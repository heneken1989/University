package com.aptech.group3.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
