package com.aptech.group3.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.Dto.ActionStatus;
import com.aptech.group3.Dto.SemesterEditDto;
import com.aptech.group3.Dto.SemeterDto;
import com.aptech.group3.Dto.SubjectCreateDto;
import com.aptech.group3.Dto.SubjectEditDto;
import com.aptech.group3.Repository.SemesterRepository;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.SemesterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Valid;

@Controller
@RequestMapping({"/admin/semester"})
public class SemeterController {
	@Autowired
	SemesterService smService;
	
	@Autowired
	SemesterRepository smRepo;
	
	@GetMapping("/listsemester")
	public String Semester(Model model,@RequestParam(name = "error", required = false) ActionStatus error,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		List<Semeter> sem = smService.findAll();
		
		model.addAttribute("sem",sem);
		if(error!=null) {
			model.addAttribute("error", error.toString());
		}
		return "semester/index";
	}
	
	@GetMapping("/createsemester")
	public String showcreate(Model model) {
		SemeterDto data = new SemeterDto();
		model.addAttribute("data",data);
		return "semester/create";
	}
	
	@PostMapping("/create")
	public String createSemester(Model model,@ModelAttribute("data")  SemeterDto data,
			BindingResult bindingResult, RedirectAttributes rm, HttpServletRequest request) {
		
		// Validate SemeterDto manually if necessary
	    if (data.getDaystart() == null || data.getDayend() == null) {
	        bindingResult.rejectValue("daystart", "error.data", "Date must not be null");
	        
	        model.addAttribute("data", data);
	        return "semester/create";
	    }else if(data.getName() == 0) {
	    	bindingResult.rejectValue("name", "error.data", "Semester name must not be null");
	    	model.addAttribute("data", data);
	        return "semester/create";
	    }
		
		if(bindingResult.hasErrors()) {
			
			
			model.addAttribute("data", data);
			return"semester/create";	
		}
		System.out.print(data);
		 smService.create(data);
		rm.addAttribute("error",ActionStatus.CREATED);
	return "redirect:/admin/semester/listsemester";
	}
	
	@GetMapping("/updatesemester/{id}")
	public String showupdateSemester(@PathVariable(name = "id") int id, Model model) {
		
		Semeter semester = smService.getSemesterById(id);
		
		List<Semeter> small= smService.findAll();
		
		model.addAttribute("small",small);
		//Semeter sm2 = smRepo.getById(null)
		model.addAttribute("semester",semester);
		return "semester/update";
	}
	
	@PostMapping("/updateSemester/{id}")
	public String saveupdate(Model model, @ModelAttribute("semester") @Valid SemesterEditDto semester, BindingResult result,
			@RequestParam(name = "id") Long id, RedirectAttributes rm,@AuthenticationPrincipal CustomUserDetails currentUser
			, HttpServletRequest request) {
	
		if (semester.getDay_start() == null || semester.getDay_end() == null ) {
			result.rejectValue("day_start", "error.data", "Date must not be null");
	        model.addAttribute("data", semester);
	        return "semester/update";
	    }else if(semester.getName() == 0) {
	    	result.rejectValue("name", "error.data", "Semester name must not be null");
	    	model.addAttribute("data", semester);
	        return "semester/update";
	    }
				
			model.addAttribute("data", semester);

		
		smService.updateSemester(semester);
		rm.addAttribute("error", ActionStatus.UPDATED);
		return "redirect:/admin/semester/listsemester";
	}
}
