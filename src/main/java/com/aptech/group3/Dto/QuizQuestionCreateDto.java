package com.aptech.group3.Dto;



import java.util.List;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class QuizQuestionCreateDto {


	  private String content;
	  private float mark;
	  private String type;
      private Long quizId;	 	 
      
      @NotEmpty(message = "There must be at least one correct answer.")
      private List<String> correctAnswers;

      private List<String> incorrectAnswers;
}
