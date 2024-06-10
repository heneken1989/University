package com.aptech.group3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aptech.group3.entity.MarkSubject;


@Service
public interface MarkSubjectService {
	public List<MarkSubject> getMarksByStudentId(Long studentId);
}
