package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;

import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;

public interface SubjectService {
	public List<SubjectLevel> listSubjectLevel();
	 public List<Subject> findBySubjectName(String name);
	 public Optional<Subject> findbyId(Long id);
	 public List<Subject> findByLevel(Long levelId);
	 public List<Subject> searchSubject(String name, Integer fieldId, Integer levelId);
	 public List<Subject> findAll();
	 public int getCredit(int id);
	 public List<Subject> getByField(int id);
  
    

}
