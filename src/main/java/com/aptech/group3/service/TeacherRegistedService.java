package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.entity.TeacherRegisted;

public interface TeacherRegistedService {

	
	public List<TeacherRegisted> getByTeacherId(Long id,Long semesterId);
}
