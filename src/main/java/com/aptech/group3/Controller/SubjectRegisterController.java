package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.SubjectService;

import com.aptech.group3.serviceImpl.StudentClassServiceImpl;
import com.aptech.group3.serviceImpl.UserServiceImpl;

import jakarta.transaction.Transactional;

@Controller
@PreAuthorize("hasAuthority('STUDENT')")
public class SubjectRegisterController {

	@Autowired
	private SubjectService subService;

	@Autowired
	private FiledRepository fRepository;

	@Autowired
	private StudentClassService studentsubservice;

	@Autowired
	private StudentClassRepository studentClassRepository;

	@Autowired
	private ClassForSubjectService classservice;

	@Autowired
	private UserServiceImpl userservice;

	@Autowired
	private ClassForSubjectRepository classForSubjectRepository;

	@GetMapping("/SubjectRegister")
	public String SubjectRegister(Model model, @AuthenticationPrincipal UserDetails currentUser) {
		User user = userservice.getUserByUserEmail(currentUser.getUsername());
		List<SubjectLevel> subjectLevels = subService.listSubjectLevel();
		List<Field> fields = user.getFields();
		model.addAttribute("fields", fields);
		model.addAttribute("subjectlevels", subjectLevels);
		return "page/RegisterSubject/New";
	}

	@PostMapping("/ClassRegister")
	@ResponseBody
	public ClassForSubjectDto ClassRegister(@RequestBody ClassForSubjectDto dto,
			@AuthenticationPrincipal UserDetails currentUser) {
		User user = userservice.getUserByUserEmail(currentUser.getUsername());
		studentsubservice.RegisterClass(dto, user.getId());
		return dto;
	}

	@PostMapping("/cancelClass")
	public String CancelClassRegister(@RequestParam Long classId) {
		StudentClass studentclass = studentClassRepository.getById(classId);
		ClassForSubject classs = studentclass.getClassforSubject();

		List<StudentClass> waitingList = studentsubservice.findEarliestByStatus("WaitingList");
		// if have waitinglist , transfer earliest Student in WaitingList to List , and
		// no need to change quantity
		if (!waitingList.isEmpty()) {
			StudentClass tranStudentClass = waitingList.get(0);
			tranStudentClass.setStatus("List");
			studentClassRepository.save(tranStudentClass);
		}

		// if have no WaitingList Student , - quantity of class by 1
		else {

			// - quantity of CLass by 1
			int quantity = classs.getQuantity();
			quantity -= 1;
			classs.setQuantity(quantity);
			classForSubjectRepository.save(classs);

		}
		studentClassRepository.delete(studentclass);
		return "redirect:/Ongoing";
	}

	@GetMapping("/listSubjectByStudentAndLevel")
	@ResponseBody
	public List<SubjectDto> listSubjectByStudentAndLevel(@RequestParam Long fieldId, @RequestParam Long subjectLevelId,
			@AuthenticationPrincipal UserDetails currentUser) {

		User student = userservice.getUserByUserEmail(currentUser.getUsername());
		List<SubjectDto> listSubjects = subService.findByStudent(student, fieldId);
		List<Subject> subjectsByLevel = subService.findByLevel(subjectLevelId);
		List<SubjectDto> filteredSubjects = new ArrayList<>();
		for (SubjectDto subjectDto : listSubjects) {
			for (Subject subject : subjectsByLevel) {
				if (subject.getId().equals(subjectDto.getId())) {
					filteredSubjects.add(subjectDto);
					break;
				}
			}
		}

		return filteredSubjects;

	}

	@GetMapping("/listClassForSubject")
	@ResponseBody
	public List<ClassForSubjectDto> listClass(@RequestParam Long subjectId,
			@AuthenticationPrincipal UserDetails currentUser) {
		User student = userservice.getUserByUserEmail(currentUser.getUsername());
		// take RegisteringList of student
		List<StudentClass> studentClasses = studentsubservice.findSubjectByStudentId(student.getId());

		// take list Class of each subject
		List<ClassForSubjectDto> listclass = classservice.findBySubjectId(subjectId);
		String[][] scheduleTable = addToSchedule(student.getId(), studentClasses);
		for (ClassForSubjectDto i : listclass) {
			int newSlotStart = i.getSlotStart();
			int newSlotEnd = i.getSlotEnd();
			int newWeekday = i.getWeekDay();
			String newSemesterType = i.getType();
			// check conflict SLot
			boolean hasConflict = checkForScheduleConflict(scheduleTable, newSlotStart, newSlotEnd, newWeekday,
					newSemesterType);
			i.setConflict(hasConflict);

			// check conflict subject name ( already have same subject in RegisteringList
			for (StudentClass s : studentClasses) {
				if (s.getClassforSubject().getSubject().getName().equals(i.getSubject().getName())) {
					i.setIsSameSubject(true);
					if (s.getClassforSubject().getName().equals((i.getName()))) {
						i.setIsSameClass(true);
					}
				} else {
					i.setIsSameSubject(false);
					i.setIsSameClass(false);
				}
			}
		}
		return listclass;
	}

	@GetMapping("/Ongoing")
	@Transactional
	public String showSchedule(Model model, @AuthenticationPrincipal UserDetails currentUser) {
		User student = userservice.getUserByUserEmail(currentUser.getUsername());
		List<StudentClass> studentClasses = studentsubservice.findSubjectByStudentId(student.getId());

		String[][] scheduleTable = addToSchedule(student.getId(), studentClasses);

		model.addAttribute("scheduleTable", scheduleTable);
		model.addAttribute("studentClasses", studentClasses);
		return "page/RegisterSubject/schedule";
	}

	private boolean checkForScheduleConflict(String[][] scheduleTable, int newSlotStart, int newSlotEnd, int newWeekday,
			String newSemesterType) {
		for (int slot = newSlotStart; slot <= newSlotEnd; slot++) {
			String existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1)]; // Check the first half of the day
			String existingClassSecond = scheduleTable[slot - 1][2 * (newWeekday - 1) + 1];
			if (newSemesterType.equals("SecondHalf")) {
				if (existingClassSecond != null && !existingClassSecond.isEmpty()) {
					return true;
				}
			}

			if (newSemesterType.equals("FirstHalf")) {
				if (existingClass != null && !existingClass.isEmpty()) {
					return true;
				}

			}

			if (newSemesterType.equals("FullSemester")) {

				if (existingClassSecond != null && !existingClassSecond.isEmpty()) {
					return true;
				}

				existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1)];
				if (existingClass != null && !existingClass.isEmpty()) {
					return true;
				}
			}
		}
		return false;
	}

	private String[][] addToSchedule(Long studentId, List<StudentClass> studentClasses) {

		// Fake data representing schedule table
		String[][] scheduleTable = new String[12][12];
		for (StudentClass studentClass : studentClasses) {
			ClassForSubject classForSubject = studentClass.getClassforSubject();
			int slotStart = classForSubject.getSlotStart();
			int slotEnd = classForSubject.getSlotEnd();
			int weekday = classForSubject.getWeekDay();
			String semesterType = classForSubject.getType();
			String subjectInfo = classForSubject.getSubject().getName() + " " + classForSubject.getRoom().getName();

			for (int slot = slotStart; slot <= slotEnd; slot++) {
				if (semesterType.equals("FullSemester")) {
					// Populate both halves of the day
					scheduleTable[slot - 1][2 * (weekday - 1)] = subjectInfo;
					scheduleTable[slot - 1][2 * (weekday - 1) + 1] = subjectInfo;

				} else if (semesterType.equals("FirstHaftSemester")) {
					// Populate only the first half of the day
					scheduleTable[slot - 1][2 * (weekday - 1)] = subjectInfo;

				} else if (semesterType.equals("SecondHalfSemester")) {
					// Populate only the second half of the day
					scheduleTable[slot - 1][2 * (weekday - 1) + 1] = subjectInfo;

				}
			}
		}

		return scheduleTable;
	}

}
