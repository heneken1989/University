package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClassSubjectAllDto {
	private String name;
	private int quantity;
	private Long semeter_id;
	private String status = "waiting";
	private ClassSubjectBasicDto action;
	private ClassSubjectBasicDto theory;
	private Long subject_id;
}
