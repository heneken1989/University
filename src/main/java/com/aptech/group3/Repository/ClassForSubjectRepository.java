package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.ClassForSubject;




public interface ClassForSubjectRepository extends JpaRepository<ClassForSubject,Long> {
	List<ClassForSubject> findBySubjectId(Long id);

}
