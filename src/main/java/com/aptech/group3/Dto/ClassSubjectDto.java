package com.aptech.group3.Dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassSubjectDto {
	

	private Long id;
	private String name;
	private Date dateStart;
	private Date dateEnd;
	private int slotStart;
	private int slotEnd;
	private int quantity;
	private String description;
	private String status;
	private int weekDay;
	private String type;

	private String teacherName;
	private String subjectName;
	private String roomName;
	private String semesterName;

}
