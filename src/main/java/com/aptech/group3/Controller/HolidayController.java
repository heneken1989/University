package com.aptech.group3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.HolidayCreateDto;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.HolidayService;
import com.aptech.group3.service.SemesterService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/holiday" })
public class HolidayController {
	
	@Autowired SemesterService semesterService;
	@Autowired ClassForSubjectService classService;
	@Autowired HolidayService holidayService;
	
	@GetMapping("/index")
	public String index(Model model) {
		return "holiday/index";
	}
	
	@GetMapping("/create")
	public String createShow(Model model,@AuthenticationPrincipal CustomUserDetails currentUser) {
		
		HolidayCreateDto dto = new HolidayCreateDto();
		model.addAttribute("data",dto); 
		Semeter semester=semesterService.getCurrentSemester();
		model.addAttribute("semester",semester);
		model.addAttribute("class",classService.getAllByfieldAndSemester(semester.getId(), currentUser.getUser().getFields().get(0).getId()));
		
		
		return "holiday/create";
	}
	
	@PostMapping("/create")
	public String create(Model model, @ModelAttribute("data")  HolidayCreateDto data ) {
		
		holidayService.create(data);
		 return "redirect:/holiday/index";
	}
	

}
