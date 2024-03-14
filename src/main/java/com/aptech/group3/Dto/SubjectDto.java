package com.aptech.group3.Dto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;



public class SubjectDto {
	

	  private Long id;
	  private String name;
	  private int credit;
	  private Long subjectlevel_Id;
	  private Long field_Id;
	
}
