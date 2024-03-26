package com.aptech.group3.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentClass {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
	
	@ManyToOne
	@JoinColumn(name="student_id" , referencedColumnName="id")
	 private User student;
	
	
	@ManyToOne
	@JoinColumn(name="class_id" , referencedColumnName="id")
	 private ClassForSubject classforSubject;
	
	 private String status;
	 private Date createDate;
	
    
}
