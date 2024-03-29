package com.aptech.group3.entity;

import java.util.Date;
import java.util.List;

import org.modelmapper.internal.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.BatchAllocator.ForTotal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity

public class QuizExam {
	    @Id
     	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	  private Date startDate;
	  private Date endDate;
	  private float totalMark;
	  
	    @ManyToOne
	    @JoinColumn(name = "user_id")
	    private User student;
	    
	    @ManyToOne
	    @JoinColumn(name = "quiz_id")
	    private Quiz quiz;
	   
}