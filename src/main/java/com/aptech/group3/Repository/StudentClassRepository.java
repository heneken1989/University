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
	
	//new
		@Query("SELECT o FROM StudentClass o WHERE o.student.id = :studentId AND "
				+ " o.classforSubject.semeter.id= :semesterId "
				+ " AND :dateStart >= o.classforSubject.dateStart  AND :dateEnd<= o.classforSubject.dateEnd" )
		 Optional<StudentClass> getcalendar(Long studentId, Date dateStart,Date dateEnd, Long semesterId);
		
		
      List<StudentClass> findByStudentId(Long studentId);
      
      List<ClassForSubject> findClassForSubjectsByStudentId(Long studentId);

       List<StudentClass> findByStudent_Id(Long studentId);
      
      
       StudentClass findByStudent_IdAndClassforSubject_Id(Long sId ,Long cId);
       
	
       
      
      // Assuming createDate is a field of type Date in StudentClass
      @Query("SELECT sc FROM StudentClass sc WHERE sc.status = :status ORDER BY sc.createDate ASC")
      List<StudentClass> findEarliestByStatus(@Param("status") String status);
}
