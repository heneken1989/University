package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.aptech.group3.entity.Subject;



public interface SubjectRepository extends JpaRepository<Subject,Long> {
	List<Subject> findByNameContainingIgnoreCase(String name);
	
	List<Subject> findBySubjectlevelId(Long id);
	
	 @Query("SELECT s FROM Subject s WHERE " +
	           "(:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
	           "(:subjectlevelId IS NULL OR s.subjectlevel.id = :subjectlevelId) AND " +
	           "(:fieldId IS NULL OR s.field.id = :fieldId)")
	    List<Subject> findByMultipleCriteria( String name,Integer subjectlevelId,Integer fieldId);
	 
	 @Query("SELECT s.credit FROM Subject s WHERE s.id= :id ")
	 int getCreditById(int id);
	 
	 List<Subject> findByFieldId(int fieldId);
}
