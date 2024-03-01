package com.aptech.group3.entity;

import lombok.Getter;
import lombok.Setter;



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
public class StudentSubject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
	
	@ManyToOne
	@JoinColumn(name="student_id" , referencedColumnName="userid")
	 private User user;
	
	
	@ManyToOne
	@JoinColumn(name="subject_id" , referencedColumnName="id")
	 private Subject subject;
	
    
}
