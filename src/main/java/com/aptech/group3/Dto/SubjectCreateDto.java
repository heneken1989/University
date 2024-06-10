package com.aptech.group3.Dto;
import java.util.Set;

import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.SubjectLevel;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SubjectCreateDto {
	 private Long id;
	 
	 @NotEmpty(message="{subject.name.error}")
	    private String name;
	    @NotNull(message="vui lòng nhập tín chỉ")
		@Min(value=1,message="nhỏ nhất là 1")
	    @Max(value=4,message="lớn nhất là 4")
	    private int credit;
	    private ClassType type;
	    private Integer creditAction;
	    
	    private Long field_id;
	    @NotNull(message="vui lòng chọn level")
	    private Long subjectlevel_id ;
	    
	    private Set<RequiredSubject> requiredSubjects;
	    private Set<RequiredSubject> Pass;
	    private Set<RequiredSubject> OptionalPass;
}
