package com.aptech.group3.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;



public interface StudentClassRepository extends JpaRepository<StudentClass ,Long> {
      List<StudentClass> findByStudentId(Long studentId);
      List<ClassForSubject> findClassForSubjectsByStudentId(Long studentId);
      // Assuming createDate is a field of type Date in StudentClass
      @Query("SELECT sc FROM StudentClass sc WHERE sc.status = :status ORDER BY sc.createDate ASC")
      List<StudentClass> findEarliestByStatus(@Param("status") String status);
}
