package com.aptech.group3.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Subject;



public interface SubjectRepository extends JpaRepository<Subject,Long> {
	Optional<Subject> findById(Long id);
	List<Subject> findByNameContainingIgnoreCase(String name);
	List<Subject> findBySubjectlevelId(Long id);
	

}
