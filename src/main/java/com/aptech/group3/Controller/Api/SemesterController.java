package com.aptech.group3.Controller.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.SubjectService;

@RestController
@RequestMapping({"/api"})
public class SemesterController {
	
	
	@Autowired
	SubjectService ss;
	
	
	@Autowired
	ClassForSubjectService classSubjectService;
	
	@GetMapping("/credit")
	public int getCreditOfSubject( @RequestParam("id") int id) {
		return ss.getCredit(id);
	}
	


}
