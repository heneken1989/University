package com.aptech.group3.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.Dto.AttendanceDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.LessonSubject;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.TeacherRegisted;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.AttendanceService;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.LessonSubjectService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.TeacherRegistedService;

@Controller
@RequestMapping({ "/attendance" })
public class AttendanceController {

	@Autowired private TeacherRegistedService teacherService;

	@Autowired private StudentClassService classService;


	@Autowired private AttendanceService attendanceService;
	
	@Autowired private SemesterService semesterService;
	
	@Autowired private LessonSubjectService lessonService;
	
@Autowired private ClassForSubjectService  classSubjectService;
	
	
	
	
	@GetMapping("/list")
	public String list(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam(name="show", required = false, defaultValue="today") String show,
			@RequestParam(name="error", required = false) String error ) {
		
		
		
		Long teacherId = currentUser.getUserId();
		Semeter currentSemester= semesterService.getCurrentSemester();
		List<TeacherRegisted> data;
		if(show==null || show.equals("all") ) {
			data= teacherService.getByTeacherId(teacherId, currentSemester.getId());
		}else {
			data=teacherService.getListClassByDay(teacherId, new Date());
		}
		
		model.addAttribute("data",data);
		model.addAttribute("show",show);
		
			model.addAttribute("error",error);
		
		
		
		return "attendance/list";
	}

	@GetMapping("/index/{id}")
	public String index(Model model, @PathVariable(name = "id") Long classId,
			@RequestParam(name="code", required = false, defaultValue="") String code,
			@AuthenticationPrincipal CustomUserDetails currentUser , RedirectAttributes redirectAttribute ) {

		Long teacherId = currentUser.getUserId();

		boolean check = teacherService.checkTeacherOwnClass(teacherId, classId);

		if (!check) {
			model.addAttribute("error", "just attendance  for your class");
			return "attendance/list";
		}
		
		List< LessonSubject> currentLesson= lessonService.getCurrentLesson(classId, new Date());
		if(currentLesson.isEmpty()) {
			
			redirectAttribute.addAttribute("error","No  lesson for this class today ");
			return "redirect:/attendance/list";
		}
		List<AttendanceDto> data;
	
		model.addAttribute("lesson", currentLesson.get(0));

		List<AttendanceDto> attended = attendanceService.getListAttendanceDto(currentLesson.get(0).getId(), new Date());
		if(code.isBlank()) {
			data = classService.getListStudentInClass(classId);
		}else {
			data=classService.getListStudentByCode(code, classId);
		}
		

		if (!attended.isEmpty()) {
			attended.forEach(e -> {
				data.forEach(d -> {
					if (d.getStudent_id() == e.getStudent_id()) {
						d.setStatus(e.getStatus());
					}
				});
			});
		}

		model.addAttribute("data", data);
		model.addAttribute("id", classId);

		return "attendance/index";
	}

}
