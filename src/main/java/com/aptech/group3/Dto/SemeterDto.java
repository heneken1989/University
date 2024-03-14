package com.aptech.group3.Dto;



import java.util.Date;

import lombok.Data;


@Data
public class SemeterDto {

    private Long id;
	private String name;
	private int year;
	private Date day_start;
	private Date day_end;
}