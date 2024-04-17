package com.aptech.group3.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;

import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.SubjectService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/class" })
public class ClassSubjectController {

	@Autowired
	SemesterService SemesterService;

	@Autowired
	SubjectService subjectService;

	@Autowired
	ClassForSubjectService classSubjectService;
	
	@GetMapping("/detail/{id}")
	public String detail(Model model ,  @PathVariable(name = "id") int id) {

		model.addAttribute("data",classSubjectService.findByClassId(id));
		return "class_subject/detail";
	}

	@GetMapping("/list")
	public String showByField(Model model, @AuthenticationPrincipal CustomUserDetails currentUser, 
			@RequestParam(name = "subject", required = false) Integer sj,
			@RequestParam(name = "semester", required = false) Integer se,
			@RequestParam(name = "page", defaultValue = "1") int page) {
		Pageable paging = PageRequest.of(page - 1, 2);
		int subjectId= sj== null ? 0 :sj;
		int selectSemester = se == null ? SemesterService.getCurrentSemester().getId().intValue() : se;
		Page<ClassForSubject> data = classSubjectService.getSubjectByFieldAndSemester(2, selectSemester, subjectId== 0 ? null :sj, paging);
		model.addAttribute("data", data);
		model.addAttribute("semesters", SemesterService.findAll());
		model.addAttribute("currentSemester", selectSemester);
		model.addAttribute("currentSubject", subjectId);
		model.addAttribute("subjects",subjectService.getByField(currentUser.getUser().getFields().get(0).getId()));
		
	

		return "class_subject/index";
	}

	@GetMapping("/create")
	public String showCreate(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		ClassSubjectCreateDto data = new ClassSubjectCreateDto();
		model.addAttribute("semester", SemesterService.getCurrentSemester());
		model.addAttribute("data", data);
		ClassSubjectAllDto dataAll = new ClassSubjectAllDto();
		model.addAttribute("dataAll", dataAll);
		model.addAttribute("subjects", subjectService.getByField(currentUser.getUser().getFields().get(0).getId()));
		model.addAttribute("error","noError");
		return "class_subject/create";
	}

	@PostMapping("/create")
	public String create(Model model, @ModelAttribute("data") @Valid ClassSubjectCreateDto data,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("semester", SemesterService.getCurrentSemester());
			model.addAttribute("data", data);
			model.addAttribute("error","error");
			
			ClassSubjectAllDto dataAll = new ClassSubjectAllDto();
			model.addAttribute("dataAll", dataAll);
			model.addAttribute("subjects", subjectService.findAll());
			return "class_subject/create";
		}
		
		
		classSubjectService.create(data);
		return "index";
	}

	@PostMapping("/createall")
	public String createAll(Model model, @ModelAttribute("dataAll") ClassSubjectAllDto data) {
		classSubjectService.createAll(data);
		return "index";
	}

}