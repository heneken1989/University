package com.aptech.group3.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.TeacherRegistedRepository;
import com.aptech.group3.entity.TeacherRegisted;
import com.aptech.group3.service.TeacherRegistedService;

@Service
public class TeacherRegistedServiceImpl implements TeacherRegistedService {

	
	@Autowired 
	TeacherRegistedRepository teacherRepo;
	
	public List<TeacherRegisted> getByTeacherId(Long id,Long semesterId){
		return teacherRepo.findByTeacherIdAndSemesterId(id,semesterId);
	}
}
