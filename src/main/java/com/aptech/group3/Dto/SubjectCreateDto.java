package com.aptech.group3.Dto;
import java.util.Set;

import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.SubjectLevel;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SubjectCreateDto {
	 private Long id;
	 
	 @NotEmpty(message="{admin.subject.name.error}")
	 @Pattern(regexp = "^(?!^[0-9]+$)(?!^(.)\\\\1{3,}$)[a-zA-Z0-9 ]{4,12}$",
     message = "name must be of 4 to 12 length with no special characters")
    private String name;
	 
	 @NotNull(message="{admin.subject.credit.error}")  
	    private int credit;
	    
	 @NotNull(message="{admin.subject.type.error}")
	    private ClassType type;
          

	    private Integer creditAction;
	    
	    private Long field_id;
	    
	    @NotNull(message="{admin.subject.subjectlevel_id.error}")
	    private Long subjectlevel_id ;
	    
	    private Set<RequiredSubject> requiredSubjects;
	    private Set<RequiredSubject> Pass;
	    private Set<RequiredSubject> OptionalPass;
}
