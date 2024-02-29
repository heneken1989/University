package com.aptech.group3.entity;

import java.sql.Date;

import com.aptech.group3.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;




@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Data

public class ClassForSubject {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id ;
	private String name;
	private Date time;
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="teacher_id" , referencedColumnName="userid")
	 private User user;
	
	
	@ManyToOne
	@JoinColumn(name="subject_id" , referencedColumnName="id")
	 private Subject subject;
}
