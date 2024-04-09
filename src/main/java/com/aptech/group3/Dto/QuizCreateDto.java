package com.aptech.group3.Dto;

import java.util.Date;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizCreateDto {

	  private String name;
	  
	  private int duration;
	  
	  private String type;
	  
	  private float  totalMark;
	  
	  private Date   createDate;
	  
	   @NotNull(message = "A subject must be selected.")
      private Long   subject_id;
	   
}
