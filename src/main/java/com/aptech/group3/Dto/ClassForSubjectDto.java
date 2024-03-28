package com.aptech.group3.Dto;



import java.util.Date;

import com.aptech.group3.entity.Room;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.Subject;


import lombok.Data;


@Data

public class ClassForSubjectDto {

    Long id ;
	private String name;
	private Date dateStart;
	private Date dateEnd;
	private int slotStart;
	private int slotEnd;
	private int quantity;
	private String description;
	private String status;
	private int weekDay;
	private UserDto teacher;
	private Subject subject;
	private Room room;
	private Semeter semeter;
	private String type;
	
	private Boolean conflict;
	private Boolean isSameSubject;
	private Boolean isSameClass;
	
	
	

	
}
