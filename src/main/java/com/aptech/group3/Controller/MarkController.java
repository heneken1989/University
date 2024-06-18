package com.aptech.group3.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.Dto.ActionStatus;
import com.aptech.group3.Dto.MarkSubjectCreateDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.MarkSubject;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.MarkSubjectService;
import com.aptech.group3.service.SemesterService;
import com.google.api.client.http.HttpRequest;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Controller

public class MarkController {

	@Autowired
	private MarkSubjectService markSubjectService;
	@Autowired
	private ClassForSubjectService classService;
	@Autowired
	private SemesterService semesterService;


	@GetMapping("/admin/mark/insert/{id}")
	public String showInsertForm(@PathVariable("id") Long classId, Model model) {
	    List<StudentClass> studentClasses = classService.findStudentClassesByClassId(classId);

	    List<MarkSubjectCreateDto> markSubjectCreateDtoList = new ArrayList<>();
	    List<Long> studentIds = new ArrayList<>(); // List to hold student IDs
	    for (StudentClass studentClass : studentClasses) {
	        MarkSubjectCreateDto dto = new MarkSubjectCreateDto();
	        dto.setClassId(classId);
	        dto.setStudentId(studentClass.getStudent().getId());
	        // Các thuộc tính khác cần thiết

	        markSubjectCreateDtoList.add(dto);
	        studentIds.add(studentClass.getStudent().getId()); // Add student ID to the list
	    }
	    int classIdInt = classId.intValue();
	    ClassForSubject classSubjects = classService.findByClassId(classIdInt);
	    Long subjectId = classSubjects.getSubject().getId();
	    model.addAttribute("markSubjectCreateDtoList", markSubjectCreateDtoList);
	    model.addAttribute("classId", classId); // Thêm classId để submit cùng với form
	    model.addAttribute("studentClasses", studentClasses);
	    model.addAttribute("subjectId", subjectId);
	    model.addAttribute("studentIds", studentIds.stream().map(String::valueOf).collect(Collectors.joining(",")));
	   
	    return "mark/insert";
	}



	  @PostMapping("/admin/mark/insert")
	  public String insertMarks(@Valid @RequestBody List<MarkSubjectCreateDto> markSubjectCreateDtoList,
		        BindingResult result) {
	        markSubjectService.insertMarks(markSubjectCreateDtoList);
	        return "redirect:/admin/mark/list";
	    }
	@GetMapping("/admin/mark/list")
	public String getMarkAdmin(@RequestParam(name = "semesterId", required = false) Long semesterId,
			@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("EMPLOYEE"))) {
			List<Semeter> semesters = semesterService.findAll();
			model.addAttribute("semesters", semesters);

			if (semesterId == null && !semesters.isEmpty()) {
				semesterId = semesters.get(0).getId(); // Select the first semester by default
			}
			model.addAttribute("currentSemester", semesterId);

			List<ClassForSubject> classSubjects = classService.findAllBySemesterId(semesterId);
			model.addAttribute("classSubjects", classSubjects);
		} else {
			throw new IllegalStateException("User role not recognized");
		}
		return "mark/markAdmin";
	}

	@GetMapping("/admin/mark/searchmark")
	public String searchClasses(@RequestParam(name = "className", required = false) String className, Model model) {
		List<ClassForSubject> classes;
		List<Semeter> semesters = semesterService.findAll();
		System.out.println("ClassName: " + className);
		if (className != null && !className.isEmpty()) {
			classes = classService.findClassesByName(className);
		} else {
			classes = classService.findAll();
		}

		model.addAttribute("semesters", semesters);
		model.addAttribute("classSubjects", classes);

		return "mark/markAdmin";
	}

	@GetMapping("/web/mark/export")
	public void exportToExcel(@RequestParam(name = "classId", required = false) Long classId,
			@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletResponse response, HttpSession session, Model model)
			throws IOException {
		Long userId = userDetails.getUserId();
		List<MarkSubject> markSubjects;

		if (classId != null) {
			markSubjects = markSubjectService.getListMarkSubjectByClassId(classId);
			session.setAttribute("classId", classId);
		} else {
			Long sessionClassId = (Long) session.getAttribute("classId");
			if (sessionClassId != null) {
				markSubjects = markSubjectService.getListMarkSubjectByClassId(sessionClassId);
			} else {
				Long defaultClassId = classService.getClassSubjectIdByTeacherId(userId);
				markSubjects = markSubjectService.getListMarkSubjectByClassId(defaultClassId);
			}
		}

		if (markSubjects.isEmpty()) {
	       
			return ;
		}

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=mark.xlsx");

		markSubjectService.exportToExcel(markSubjects, response.getOutputStream());
	}

	@GetMapping("/web/mark/getMarkSubject")
	public String getMarkSubject(@RequestParam(name = "classId", required = false) Long classId,
			@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		Long id = userDetails.getUserId();
		System.out.println("ClassId" + classId);
		if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("STUDENT"))) {
			List<MarkSubject> markSubjects = markSubjectService.getMarksByStudentId(id);
			model.addAttribute("markSubjects", markSubjects);
		} else if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("TEACHER"))) {
			if (classId == null) {
				Long defaultClassId = classService.getClassSubjectIdByTeacherId(id);
				if (defaultClassId == null) {
					model.addAttribute("message", "You haven't taught any classes yet");
					model.addAttribute("hideTable", true);
				} else {
					List<MarkSubject> markSubjects = markSubjectService.getListMarkSubjectByClassId(defaultClassId);
					model.addAttribute("markSubjects", markSubjects);
				}
			} else {
				List<MarkSubject> markSubjects = markSubjectService.getListMarkSubjectByClassId(classId);
				model.addAttribute("markSubjects", markSubjects);
			}
		} else {
			throw new IllegalStateException("User role not recognized");
		}
		return "mark/list";
	}

	@PreAuthorize("hasAuthority('TEACHER')")
	@GetMapping("/web/mark/getClassMarks")
	public String getListMarkSubject(@RequestParam("classId") Long classId, Model model) {
		List<MarkSubject> markSubjects = markSubjectService.getListMarkSubjectByClassId(classId);
		model.addAttribute("markSubjects", markSubjects);
		return "mark/list";
	}
}
