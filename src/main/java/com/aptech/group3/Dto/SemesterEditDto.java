package com.aptech.group3.Dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class SemesterEditDto {
	private Long id;
	
	@NotNull(message="cannot null")
    private int name;
	//@Min(2024)
	private int year;
	
	//@NotNull(message="cannot null")

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	
	private Date day_start;
	
	@NotNull(message="cannot null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date day_end;
	
	@NotNull(message="{cannot null}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startRegisDate;
	
	@NotNull(message="{cannot null}")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date closeRegisDate;
}
