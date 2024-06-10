package com.aptech.group3.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.group3.entity.MarkSubject;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.MarkSubjectService;

import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;

@Controller

@RequestMapping({ "/web/mark" })

public class MarkController {

	@Autowired
	private MarkSubjectService markSubjectService;

	@PreAuthorize("hasAuthority('STUDENT')")	
	@GetMapping("/getMarkSubject")
	public String getMarkSubject(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
		Long studentId = userDetails.getUserId();

		System.out.println("id: " + studentId);
		List<MarkSubject> markSubjects = markSubjectService.getMarksByStudentId(studentId);
		model.addAttribute("markSubjects", markSubjects);
		return "mark/list";
	}
}
