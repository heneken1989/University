package com.aptech.group3.Dto;



import java.util.Date;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class SemeterDto {

    private Long id;
	private int name;
	private int year;
	private Date day_start;
	private Date day_end;
}