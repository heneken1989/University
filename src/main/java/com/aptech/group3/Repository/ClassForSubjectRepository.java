package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.ClassForSubject;




public interface ClassForSubjectRepository extends JpaRepository<ClassForSubject,Long> {
	List<ClassForSubject> findBySubjectId(Long id);
	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id = :semesterId AND "
			+ " s.subject.field.id = :fieldId " + " AND (:subjectId IS NULL OR s.subject.id = :subjectId )")

	Page<ClassForSubject> findByFieldIdAndSubjectId(int semesterId, int fieldId, Integer subjectId, Pageable pageable);
	
	public ClassForSubject findById(int id);

}
