package com.aptech.group3.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.ClassSubjectEditOneDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.RoomRegistedService;
import com.aptech.group3.service.RoomService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.service.TeacherRegistedService;
import com.aptech.group3.service.TeacherSubjectService;

import jakarta.validation.Valid;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')") 
@RequestMapping({ "/admin/class" })
public class ClassSubjectController {

	@Autowired private SemesterService SemesterService;

	@Autowired private SubjectService subjectService;

	@Autowired private ClassForSubjectService classSubjectService;
	
	@Autowired private RoomService roomService;
	
	@Autowired private TeacherRegistedService teacherRegistedService;
	
	@Autowired private RoomRegistedService roomRegistedService;
	
	@Autowired private TeacherSubjectService teacherService;
	
	@GetMapping("/edit/{id}")
	public String showEdit(Model model ,  @PathVariable(name = "id") Long id) {
		
		boolean check= classSubjectService.checkType(id);
		
		
			ClassSubjectEditOneDto data= classSubjectService.getEditDto(id);
			model.addAttribute("semester", SemesterService.getCurrentSemester());
			model.addAttribute("error",1);
			model.addAttribute("data",data);
			model.addAttribute("current",classSubjectService.findById(id));
		
		
		
		

		return "class_subject/edit";
	}
	
	@PostMapping("/edit/one")
	public String editOne(Model model, @ModelAttribute("data") @Valid ClassSubjectEditOneDto dto,BindingResult bindingResult ) {
		ClassForSubject check= classSubjectService.findById(dto.getId());
		
		if(bindingResult.hasErrors()) {
			ClassSubjectEditOneDto data= classSubjectService.getEditDto(dto.getId());
			model.addAttribute("semester", SemesterService.getCurrentSemester());
			model.addAttribute("error",1);
			model.addAttribute("data",data);
			model.addAttribute("current",classSubjectService.findById(dto.getId()));
			model.addAttribute("room",roomService.getAvailableRoom(data.getQuantity(),data.getWeekDay() , 
					data.getSlotStart(), data.getDateStart(), data.getDateEnd()));
			model.addAttribute("teacher",teacherService.getAvailableTeacher(data.getSubject_id().intValue(), data.getWeekDay(), data.getSlotStart(), 
					data.getDateStart(), data.getDateEnd()));
			return "class_subject/edit";
		}

	
		
		if(check.getRoom().getId()!= dto.getRoom_id()) {
			roomRegistedService.update(check.getId(),dto.getRoom_id());
		}
		
		if(check.getTeacher().getId()!= dto.getTeacher_id()) {
			teacherRegistedService.update(check.getId(),dto.getTeacher_id());
		}
		classSubjectService.Edit(dto);
		return "redirect:/admin/class/list";
	}
	
	
	@GetMapping("/detail/{id}")
	public String detail(Model model ,  @PathVariable(name = "id") Long id) {

		model.addAttribute("data",classSubjectService.findById(id));
		return "class_subject/detail";
	}

	@GetMapping("/list")
	public String showByField(Model model, @AuthenticationPrincipal CustomUserDetails currentUser, 
			@RequestParam(name = "subject", required = false) Integer sj,
			@RequestParam(name = "semester", required = false) Integer se,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		Pageable paging = PageRequest.of(page - 1, 10);
		int subjectId= sj== null ? 0 :sj;
		Long fieldId=currentUser.getUser().getFields().get(0).getId();
		int selectSemester = se == null ? SemesterService.getCurrentSemester().getId().intValue() : se;
		Page<ClassForSubject> data = classSubjectService.getSubjectByFieldAndSemester(fieldId.intValue(), selectSemester, subjectId== 0 ? null :sj, paging);
		model.addAttribute("data", data);
		model.addAttribute("semesters", SemesterService.findAll());
		model.addAttribute("currentSemester", selectSemester);
		model.addAttribute("currentSubject", subjectId);
		model.addAttribute("subjects",subjectService.getByField(fieldId));
		
	

		return "class_subject/index";
	}

	@GetMapping("/create")
	public String showCreate(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		ClassSubjectCreateDto data = new ClassSubjectCreateDto();
		Semeter current= SemesterService.getCurrentSemester();
		Semeter nextSemeter;
		
		if(current.getName()==1) {
			nextSemeter= SemesterService.getByYearAndName(current.getYear(), current.getName()+1);
		}else {
			nextSemeter= SemesterService.getByYearAndName(current.getYear()+1, current.getName()-1);
		}
		
	    model.addAttribute("semester", nextSemeter);
		model.addAttribute("data", data);
		model.addAttribute("error",0);
		ClassSubjectAllDto dataAll = new ClassSubjectAllDto();
		model.addAttribute("dataAll", dataAll);
		model.addAttribute("subjects", subjectService.getByField(currentUser.getUser().getFields().get(0).getId()));
		return "class_subject/create";
	}

	@PostMapping("/create")
	public String create(Model model, @ModelAttribute("data") @Valid ClassSubjectCreateDto data,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("semester", SemesterService.getCurrentSemester());
			model.addAttribute("data", data);
			model.addAttribute("error",1);
			
			ClassSubjectAllDto dataAll = new ClassSubjectAllDto();
			model.addAttribute("dataAll", dataAll);
			model.addAttribute("subjects", subjectService.findAll());
			return "class_subject/create";
		}
		
		
		
		
		classSubjectService.create(data);
		return "redirect:/admin/class/list";
	}

	@PostMapping("/createall")
	public String createAll(Model model, @ModelAttribute("dataAll") @Valid ClassSubjectAllDto dataAll,BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("semester", SemesterService.getCurrentSemester());
			ClassSubjectCreateDto data= new ClassSubjectCreateDto();
			model.addAttribute("data", data);
		
			model.addAttribute("dataAll", dataAll);
			model.addAttribute("subjects", subjectService.findAll());
			model.addAttribute("error",1);
			return "class_subject/create";
		}
		
	
		classSubjectService.createAll(dataAll);
		return "redirect:/admin/class/list";
	}

}