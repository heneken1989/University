	package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.ClassForSubject;




public interface ClassForSubjectRepository extends JpaRepository<ClassForSubject,Long> {
	
	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id=:semesterId AND s.subject.field.id=:fieldId ")
	List<ClassForSubject> findBySemeter_IdAndSubject_field_Id(Long semesterId, Long fieldId );
	
	
	
	List<ClassForSubject> findBySubjectId(Long id);
	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id = :semesterId AND "
			+ " s.subject.field.id = :fieldId " + " AND (:subjectId IS NULL OR s.subject.id = :subjectId )")

	Page<ClassForSubject> findByFieldIdAndSubjectId(int semesterId, int fieldId, Integer subjectId, Pageable pageable);
	
	
	public ClassForSubject findById(int id);
	
	//HIEN
	public List<ClassForSubject> findByTeacherId(Long id);
	
	//thanh thêm để lấy học kỳ theo lớp có học kỳ = nhau
	public List<ClassForSubject> findAll();
	
	@Query(" SELECT s FROM class_subject s WHERE s.semeter.id = :semesterId AND "
			+ " s.subject.field.id = :fieldId ")
	public List<ClassForSubject> findBySemeterIdAnd(Long semesterId, Long fieldId);
}
