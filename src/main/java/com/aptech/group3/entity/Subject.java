package com.aptech.group3.entity;
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


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Subject {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	
	  private String name; // LomBok khong duoc viet hoa
	  
	  private int credit;
      
	  
	  @ManyToOne
	  @JoinColumn(name="subjectlevel_id")
	  private SubjectLevel subjectlevel;
	  
	  @ManyToOne
	  @JoinColumn(name="field_id")
	  private Field field;
	
}
