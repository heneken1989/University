package com.aptech.group3.service;

import java.util.Date;
import java.util.List;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.LessonSubject;

public interface LessonSubjectService {
	public void create(ClassForSubject subject);
	
	public List< LessonSubject> getCurrentLesson(Long classId, Date day);
}
