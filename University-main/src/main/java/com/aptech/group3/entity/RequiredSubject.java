package com.aptech.group3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RequiredSubject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long id;
	 @ManyToOne
	  @JoinColumn(name="refer_subject_id")
	  private Subject refer_subject;
	  
	  @ManyToOne
	  @JoinColumn(name="required_subject_id")
	  private Subject required_subject;
	  
	  	  
}
