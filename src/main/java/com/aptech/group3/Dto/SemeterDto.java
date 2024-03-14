package com.aptech.group3.Dto;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class SemeterDto {

    private Long id;
	private String name;
	private int year;
	private Date day_start;
	private Date day_end;
}