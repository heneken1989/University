package com.aptech.group3.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.LessonSubjectRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Holiday;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.service.HolidayService;
import com.aptech.group3.service.LessonSubjectService;
import com.aptech.group3.service.SemesterService;

import shared.BaseMethod;

@Service
public class LessonSubjectServiceImpl implements LessonSubjectService {

	@Autowired
	LessonSubjectRepository repo;

	@Autowired
	ClassForSubjectRepository classRepo;

	@Autowired
	SemesterService semeterService;

	@Autowired
	HolidayService holidayService;
	
	public List< LessonSubject> getCurrentLesson(Long classId, Date day) {
		return repo.getLessonByDay(classId, day);
	}

	public void create(ClassForSubject subject) {
		Semeter currentSemester = semeterService.getCurrentSemester();
		List<Holiday> listHoliday = holidayService.getHolidayByYear(currentSemester.getYear());

		int numerOfWeek = 0;

		Calendar start = Calendar.getInstance();
		start.setTime(subject.getDateStart());
		Calendar end = Calendar.getInstance();
		end.setTime(subject.getDateEnd());

		if (subject.getType().equals("all")) {

			numerOfWeek = end.get(Calendar.WEEK_OF_YEAR) - start.get(Calendar.WEEK_OF_YEAR);

			for (int i = 0; i < numerOfWeek; i++) {
				Calendar date = Calendar.getInstance();
				date.setTime(subject.getDateStart());
				date.add(Calendar.DATE, i * 7);
				LessonSubject lesson = new LessonSubject();
				listHoliday.forEach(e -> {
					if (BaseMethod.customCompareDate(e.getDate(), date.getTime()) == 0) {
						lesson.setType("holiday");
					} else {
						lesson.setType("theory");
					}
				});
				lesson.setDay(date.getTime());
				lesson.setClass_subject(subject);
				lesson.setLesson(i + 1);

				repo.save(lesson);
			}
		}

		if (subject.getType().equals("fhalf") || subject.getType().equals("lhalf")) {
			int calculate = end.get(Calendar.WEEK_OF_YEAR) - start.get(Calendar.WEEK_OF_YEAR);
			if (calculate % 2 == 0) {
				numerOfWeek = calculate / 2;
			} else {
				numerOfWeek = (calculate / 2) + 1;
			}

			for (int i = 0; i < numerOfWeek; i++) {

				Calendar date = Calendar.getInstance();
				date.setTime(subject.getDateStart());
				date.add(Calendar.DATE, i * 7);
				LessonSubject lesson = new LessonSubject();
				listHoliday.forEach(e -> {
					if (BaseMethod.customCompareDate(e.getDate(), date.getTime()) == 0) {
						lesson.setType("holiday");
					} else {
						lesson.setType("theory");
					}
				});
				lesson.setDay(date.getTime());
				lesson.setClass_subject(subject);
				lesson.setLesson(2*i);

				repo.save(lesson);

				date.setTime(subject.getDateStart());
				date.add(Calendar.DATE, i * 7);
				LessonSubject lesson2 = new LessonSubject();
				listHoliday.forEach(e -> {
					if (BaseMethod.customCompareDate(e.getDate(), date.getTime()) == 0) {
						lesson2.setType("holiday");
					} else {
						lesson2.setType("theory");
					}
				});
				lesson2.setDay(date.getTime());
				lesson2.setClass_subject(subject);
				lesson2.setLesson(2*i + 1);

				repo.save(lesson2);

			}
		}

	}

}
