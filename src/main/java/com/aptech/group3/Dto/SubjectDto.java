package com.aptech.group3.Dto;

import java.util.Set;

import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.SubjectLevel;

import lombok.Data;

@Data
public class SubjectDto {
	

    private Long id;
    private String name;
    private int credit;
    private SubjectLevel subjectLevel;
    private Field field;
    private Set<RequiredSubject> requiredSubjects;
	
}
