package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aptech.group3.entity.TeacherRegisted;

public interface TeacherRegistedRepository  extends JpaRepository<TeacherRegisted, Long> {
	
	public List<TeacherRegisted> findByTeacherIdAndSemesterId(Long id,Long semesterId);

}
