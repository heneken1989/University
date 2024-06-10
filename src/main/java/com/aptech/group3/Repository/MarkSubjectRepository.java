package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.Dto.MarkSubjectDto;
import com.aptech.group3.entity.MarkSubject;


public interface MarkSubjectRepository extends JpaRepository<MarkSubject,Long> {
	    public List<MarkSubject> findByUserId(Long userId);
	}

