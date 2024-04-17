package com.aptech.group3.service;

import java.util.List;

import com.aptech.group3.Dto.HolidayCreateDto;
import com.aptech.group3.entity.Holiday;

public interface HolidayService {

	public List<Holiday> getHolidayByYear(int year);
	
	public void create(HolidayCreateDto dto);
}
