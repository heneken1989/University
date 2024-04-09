package com.aptech.group3.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;



public interface SubjectRepository extends JpaRepository<Subject,Long> {
	Optional<Subject> findById(Long id);
	List<Subject> findByNameContainingIgnoreCase(String name);
	List<Subject> findBySubjectlevelId(Long id);
	List<Subject> findBySubjectlevelIdAndFieldId(Long id,Long fieldId);
	
	@Query("SELECT DISTINCT s FROM class_subject cfs " +
	        "JOIN cfs.subject s " +
	        "WHERE " +
	        "(s IN (" +
	        "   SELECT rs.subject FROM RequiredSubject rs " +
	        "   WHERE rs.requiredsubject IN (" +
	        "       SELECT ms.subject FROM MarkSubject ms " +
	        "       WHERE ms.user = :student AND ms.mark >= 5" +
	        "   )" +
	        ") OR NOT EXISTS (SELECT rs FROM RequiredSubject rs WHERE rs.subject = s))" +
	        "AND (:fieldId IS NULL OR s.field.id = :fieldId OR :fieldId = '0')")
	List<Subject> findSubjectsForStudent(@Param("student") User student, @Param("fieldId") Long fieldId);
	
	
	 @Query("SELECT s FROM Subject s WHERE " +
	           "(:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
	           "(:subjectlevelId IS NULL OR s.subjectlevel.id = :subjectlevelId) AND " +
	           "(:fieldId IS NULL OR s.field.id = :fieldId)")
	    List<Subject> findByMultipleCriteria( String name,Integer subjectlevelId,Integer fieldId);
	 
	 @Query("SELECT s.credit FROM Subject s WHERE s.id= :id ")
	 int getCreditById(int id);
	 
	 List<Subject> findByFieldId(Long fieldId);



//eeee
	 
	
}
