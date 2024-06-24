package com.aptech.group3.Dto;

import java.util.Set;

import com.aptech.group3.entity.RequiredSubject;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubjectEditDto {
	private Long id;
	 
	 @NotEmpty(message="{admin.subject.name.error}")
	 @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$",
     message = "name must be of 4 to 12 length with no special characters")
   private String name;
	 
	 @NotNull(message="{admin.subject.credit.error}")  
	    private int credit;
	    
	 @NotNull(message="{admin.subject.type.error}")
	    private ClassType type;
         
	    private Integer creditAction;
}
