package com.aptech.group3.Dto;
import java.sql.Date;


import java.sql.Time;

import com.aptech.group3.entity.Room;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;

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
	private String style;
	
	private Boolean conflict;
	private Boolean alreadyRegis;
	
}
