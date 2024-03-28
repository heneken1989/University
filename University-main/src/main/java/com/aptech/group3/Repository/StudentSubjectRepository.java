package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.StudentSubject;



public interface StudentSubjectRepository extends JpaRepository<StudentSubject ,Long> {
      List<StudentSubject> findByUserId(Long studentId);
}
