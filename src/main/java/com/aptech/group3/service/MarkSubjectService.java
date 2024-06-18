package com.aptech.group3.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.MarkApiDto;
import com.aptech.group3.Dto.MarkDetailDto;
import com.aptech.group3.Dto.MarkSubjectCreateDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.MarkSubject;
import com.aptech.group3.entity.User;

import jakarta.servlet.ServletOutputStream;


@Service
public interface MarkSubjectService {
	
	
	public List<MarkApiDto> getListMarkDto(Long studentId);
	
	public MarkDetailDto getMarkDetailDto( Long studentId,Long ClassId);
	// new api mark
	 public List<MarkSubject> getMarksByStudentId(Long studentId);
	 public List<ClassForSubject> getClassForSubjectBySubjectId(Long subjectId);
	 public MarkSubject getMarkSubjectById(Long id);
	 public List<MarkSubject> getListMarkSubjectByClassId(Long classId);
	 public List<MarkSubject> getListMarkSubjectByStudentIdAndClassId(Long userId, Long classId);
	 public void exportToExcel(List<MarkSubject> marks, ServletOutputStream outputStream);
	 public void insertMarks(List<MarkSubjectCreateDto> markSubjectCreateDtoList) ;
 
}