package com.aptech.group3.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.service.ClassForSubjectService;

import com.aptech.group3.service.StudentSubjectService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.service.UserService;

@Controller
public class SubjectRegisterController {
	
    @Autowired
    private SubjectService subService;
    
    @Autowired
    private StudentSubjectService studentsubservice;
    
    @Autowired
    private ClassForSubjectService classservice;
    
    @Autowired
    private UserService userservice;

    

	  @GetMapping("/SubjectRegister") 
	  public String SubjectRegister(Model model) 
	    {
		  List<SubjectLevel> subjectLevels = subService.listSubjectLevel();
		  model.addAttribute("subjectlevels",subjectLevels );
	      return "page/RegisterSubject/New"; 
	    }
	  
	  @GetMapping("/getSubjectsByLevel")
	  @ResponseBody
	    public List<Subject> getSubjectsByLevel(@RequestParam Long subjectLevelId) {
	        return subService.findByLevel(subjectLevelId);
	    }
	

}
