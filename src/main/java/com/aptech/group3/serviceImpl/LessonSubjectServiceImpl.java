package com.aptech.group3.serviceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.HolidayEditDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.HolidayRepository;
import com.aptech.group3.Repository.LessonSubjectRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Holiday;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.entity.Semeter;
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
	HolidayRepository holidayRepo;
	
	
	//thanh thêm
	public List<LessonSubject> getLessonsByClassSubjectId(Long classSubjectId) {
        return repo.findByClassSubjectId(classSubjectId);
    }
	


	public void deleteLessonByClassId(Long classId) {
		repo.deleteLessonByClassId(classId);
	}

	
	public List<LessonSubject> getByClassId(Long id){
		return repo.findByClassSubject_Id(id);
	}

	//new

	
	public List<LessonSubject> getCurrentLesson(Long classId, Date day) {
		return repo.getLessonByDay(classId, day);
	}
	
	public void remove(Long holidayId ) {
		Holiday holiday= holidayRepo.findById(holidayId).orElse(null);
		if (holiday.getClassSubject() != null) {
			

			List<LessonSubject> data = repo.findByClassSubject_Id(holiday.getClassSubject().getId());
			handleRemove(holiday,holiday.getClassSubject().getId(),data);
		} else {

			List<ClassForSubject> listClass = classRepo.findBySemeter_Year(holiday.getYear());

			listClass.forEach(e -> {
				List<LessonSubject> listSubject = repo.findByClassSubject_Id(e.getId());
				handleRemove(holiday,e.getId(),listSubject);
			});
		}
	}
	
	private void  handleRemove( Holiday holiday, Long classId,List<LessonSubject> data) {
		data.forEach(e->{
			 int cws= BaseMethod.customCompareDate(e.getDay(), holiday.getDateStart());
			 int cwe= BaseMethod.customCompareDate(e.getDay(), holiday.getDateEnd());
			 
			 if(cws>=0 && cwe<=0 ) {
				 e.setType("theory");
				 repo.save(e);
				 
				 repo.delete(data.get(data.size()-1));
			 }
			 
		});
		
		
	}

	public void CheckOrRemove(HolidayEditDto dto) {
		Semeter currentSemester = semeterService.getCurrentSemester();
		
		if (dto.getClassId() != null) {

			List<LessonSubject> data = repo.findByClassSubject_Id(dto.getClassId());
			handleCheckRemove(data,dto,dto.getClassId(),currentSemester);
		} else {

			List<ClassForSubject> listClass = classRepo.findBySemeter_Year(dto.getYear());

			listClass.forEach(e -> {
				List<LessonSubject> listSubject = repo.findByClassSubject_Id(e.getId());
				handleCheckRemove(listSubject,dto,e.getId(),currentSemester);
			});
		}
		
		
	}
	
	  private void handleCheckRemove(List<LessonSubject> data, HolidayEditDto dto,Long id,	Semeter semester) {
		  List<Holiday> check = holidayRepo.getByclassSubjectOrNull(id,semester.getYear());
		  
		 List<Integer> count = new ArrayList<>();
		 check.forEach(e->{
			 data.forEach(d->{
				 
				 int cws= BaseMethod.customCompareDate(e.getDateStart(), d.getDay());
				 int cwe= BaseMethod.customCompareDate(e.getDateEnd(), d.getDay());
				 if(cws<=0 && cwe>=0) {
					 d.setType("holiday");
					 repo.save(d);
					 count.add(0);
				 }else {
					 d.setType("theory");
					 repo.save(d);
				 }
			 });
		 });
		 int last=data.size()-1;
		 if(data.size()-16 >count.size()) {
			 do {
				 repo.delete(data.get(last));
				 last--;
			 }while(count.size()== last+1);
			
		 }
		 
	  }
	 

	public void updateLesson(HolidayEditDto dto) {

		if (dto.getClassId() != null) {
			List<LessonSubject> data = repo.findByClassSubject_Id(dto.getClassId());
			handleUpdateLessonOne(data, dto,dto.getId());
		} else {
			List<ClassForSubject> listClass = classRepo.findBySemeter_Year(dto.getYear());

			listClass.forEach(e -> {
				List<LessonSubject> listSubject = repo.findByClassSubject_Id(e.getId());
				handleUpdateLessonOne(listSubject, dto, e.getId());

			});
		}
	}

	private void handleUpdateLessonOne(List<LessonSubject> data, HolidayEditDto dto, Long id) {
		Semeter currentSemester = semeterService.getCurrentSemester();
		List<Holiday> check = holidayRepo.getByclassSubjectOrNull(id,currentSemester.getYear());
		data.forEach(e -> {
			int cws = BaseMethod.customCompareDate(dto.getDateStart(), e.getDay());
			int cwe = BaseMethod.customCompareDate(dto.getDateEnd(), e.getDay());

			if (dto.getType() == "one") {
				if (cws == 0 && cwe == 0) {
					Calendar dayNew = BaseMethod.toCalendar(data.get(data.size() - 1).getDay());
					dayNew.add(Calendar.DAY_OF_MONTH, 1);
					e.setType("holiday");
					repo.save(e);

					if (data.size() - 16 != check.size()) {

						LessonSubject n = new LessonSubject();

						n.setDay(dayNew.getTime());
						n.setLesson(data.get(data.size() - 1).getLesson() + 1);
						n.setType("theory");
						n.setClassSubject(data.get(0).getClassSubject());
						repo.save(n);
					}
				}
			} else {
				if (cws <= 0 && cwe >= 0) {
					Calendar dayNew = BaseMethod.toCalendar(data.get(data.size() - 1).getDay());
					dayNew.add(Calendar.DAY_OF_MONTH, 7);
					e.setType("holiday");
					repo.save(e);

					if (data.size() - 16 != check.size()) {

						LessonSubject n = new LessonSubject();

						n.setDay(dayNew.getTime());
						n.setLesson(data.get(data.size() - 1).getLesson() + 1);
						n.setType("theory");
						n.setClassSubject(data.get(0).getClassSubject());
						repo.save(n);
					}
				}
			}
		});

	}

	public void create(ClassForSubject subject) {
		Semeter currentSemester = semeterService.getCurrentSemester();
		List<Holiday> listHoliday = holidayRepo.findByYear(currentSemester.getYear());

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
					int cws = BaseMethod.customCompareDate(e.getDateStart(), date.getTime());
					int cwe = BaseMethod.customCompareDate(e.getDateEnd(), date.getTime());
					if (cws<=0 && cwe>=0) {
						lesson.setType("holiday");
					} else {
						lesson.setType("theory");
					}
				});
				lesson.setDay(date.getTime());
				lesson.setClassSubject(subject);
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
					int cws = BaseMethod.customCompareDate(e.getDateStart(), date.getTime());
					int cwe = BaseMethod.customCompareDate(e.getDateEnd(), date.getTime());
					if (cws<=0 && cwe>=0) {
						lesson.setType("holiday");
					} else {
						lesson.setType("theory");
					}
				});
				lesson.setDay(date.getTime());
				lesson.setClassSubject(subject);
				lesson.setLesson(2 * i);

				repo.save(lesson);

				date.setTime(subject.getDateStart());
				date.add(Calendar.DATE, i * 7);
				LessonSubject lesson2 = new LessonSubject();
				listHoliday.forEach(e -> {
					int cws = BaseMethod.customCompareDate(e.getDateStart(), date.getTime());
					int cwe = BaseMethod.customCompareDate(e.getDateEnd(), date.getTime());
					if (cws<=0 && cwe>=0) {
						lesson2.setType("holiday");
					} else {
						lesson2.setType("theory");
					}
				});
				lesson2.setDay(date.getTime());
				lesson2.setClassSubject(subject);
				lesson2.setLesson(2 * i + 1);

				repo.save(lesson2);

			}
		}

	}

}
