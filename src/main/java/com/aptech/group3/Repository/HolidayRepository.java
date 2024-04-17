package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Holiday;

public interface HolidayRepository  extends JpaRepository<Holiday, Long> {

	public List<Holiday> findByYear(int year);
}
