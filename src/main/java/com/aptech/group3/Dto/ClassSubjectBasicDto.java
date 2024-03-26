package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassSubjectBasicDto {
	private String date_start;
	private String date_end;
	private int slotStart;
	private int slotEnd;
	private int weekDay;
	private Long teacher_id;
	private Long room_id;
	private String style;
}
