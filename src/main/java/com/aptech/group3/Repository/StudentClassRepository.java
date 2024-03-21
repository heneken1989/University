package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;



public interface StudentClassRepository extends JpaRepository<StudentClass ,Long> {
      List<StudentClass> findByStudentId(Long studentId);
      List<ClassForSubject> findClassForSubjectsByStudentId(Long studentId);
}
