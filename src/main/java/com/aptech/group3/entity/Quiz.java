package com.aptech.group3.entity;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Quiz {
	   @Id
    	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	   private String name;
	  private Time duration;
	  private String type;
	  private float totalMark;
	  private Date createDate;
	  
	
	   
	   @OneToMany(mappedBy="quiz", cascade = CascadeType.ALL)
	   @JsonIgnore
	   private List<QuizQuestion> quizquestions;
}
