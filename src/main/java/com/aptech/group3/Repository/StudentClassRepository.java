package com.aptech.group3.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;



public interface StudentClassRepository extends JpaRepository<StudentClass ,Long> {
	@Query("SELECT o FROM StudentClass o WHERE o.student.code LIKE %:code% AND o.classforSubject.id = :classId")
    List<StudentClass> getStudentByCodeAndClassId(String code, Long classId);
	
	
	
	List<StudentClass> findByClassforSubject_Id( Long ClassId);
	
	@Query("SELECT o FROM StudentClass o WHERE o.student.id = :studentId AND "
			+ " o.classforSubject.semeter.id= :semesterId "
			+ " AND :dateStart >= o.classforSubject.dateStart  AND :dateEnd<= o.classforSubject.dateEnd")
	List<StudentClass> getcalendar(Long studentId, Date dateStart, Date dateEnd, Long semesterId);
	
	@Query("SELECT DISTINCT o FROM StudentClass o JOIN o.classforSubject cs JOIN cs.lessons l WHERE o.student.id = :studentId AND "
	        + " cs.id = :classId "
	        + " AND DATE(l.day) = DATE(:day)")
	public StudentClass getInfoByStudentIdAndDayAndClassId(Long studentId, Long classId, Date day);
	
	
	
	//new

		List<StudentClass> findByStudentIdAndStatus(Long studentId, String status);
		
      List<StudentClass> findByStudentId(Long studentId);
      List<StudentClass> findByClassforSubjectId(Long classId);
      
      List<ClassForSubject> findClassForSubjectsByStudentId(Long studentId);

       List<StudentClass> findByStudent_Id(Long studentId);
      
      
       StudentClass findByStudent_IdAndClassforSubject_Id(Long sId ,Long cId);
       
	
       
      
      // Assuming createDate is a field of type Date in StudentClass
      @Query("SELECT sc FROM StudentClass sc WHERE sc.status = :status ORDER BY sc.createDate ASC")
      List<StudentClass> findEarliestByStatus(@Param("status") String status);
}
