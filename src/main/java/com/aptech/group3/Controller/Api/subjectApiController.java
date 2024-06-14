package com.aptech.group3.Controller.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.service.SubjectLevelService;
import com.aptech.group3.service.SubjectService;


@RestController
public class subjectApiController {
	
	@Autowired SubjectService subjectService;
	
	@Autowired SubjectLevelService sublvSbervice;
	
	@GetMapping("/api/public/list/subject")
	//@GetMapping("/create")
	public List<Subject> getList( @RequestParam(name = "field") Long fieldId, @RequestParam(name = "level") Long level){
		
		SubjectLevel current = sublvSbervice.findById(level);
		
		Long searchId =(long)0;
		
		switch(current.getName()){
		case "100":
		
			SubjectLevel check = sublvSbervice.findByName("core");
			searchId = check.getId();		
			
		 break;
		 
		case "200":
			SubjectLevel check2 = sublvSbervice.findByName("100");
			searchId = check2.getId();
			break;
			
		case "300":
			SubjectLevel check3 = sublvSbervice.findByName("200");
			searchId = check3.getId();	
			break;
		}
		
		return subjectService.getByFieldIDAndLevel(fieldId, searchId);
		
	}
	
	

}
