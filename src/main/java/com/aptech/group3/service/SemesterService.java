package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.entity.Semeter;

public interface SemesterService {
	public Semeter getCurrentSemester();
	public List<Semeter> findAll();
}
