package com.aptech.group3.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Subject;



public interface SubjectRepository extends JpaRepository<Subject,Long> {
	List<Subject> findByCategoryId(Long categoryName);
	List<Subject> findByCategoryIdGreaterThan(Long categoryId);
	List<Subject> findByCategoryIdGreaterThanAndCategoryIdLessThan(Long minCategoryId, Long maxCategoryId);
	
	Optional<Subject> findById(Long id);
	List<Subject> findByNameContainingIgnoreCase(String name);
	

}
