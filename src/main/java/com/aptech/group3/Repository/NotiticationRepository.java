package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.Notification;

public interface NotiticationRepository  extends JpaRepository<Notification, Long> {
	
	public Page<Notification> findBySemesterIdAndFieldId(Long semesterId , Long fieldId, Pageable pageable);
	
	 @Query("SELECT n FROM Notification n WHERE n.semester.id = :semesterId AND n.field.id = :fieldId ORDER BY n.id DESC")
	    Page<Notification> findBySemester_IdAndField_Id(
	             Long semesterId,
	             Long fieldId,
	            Pageable pageable
	    );
	

}
