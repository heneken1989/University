package com.aptech.group3.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class HolidayCreateDto {

	private String date_cre;
	private String name;
	private int year;
	private Long classId;
}
