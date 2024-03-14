package com.aptech.group3.Dto;

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

public class StudentSubjectDto {
     private Long id ;
	 private Long student_id;
	 private Long subject_id;
	
    
}
