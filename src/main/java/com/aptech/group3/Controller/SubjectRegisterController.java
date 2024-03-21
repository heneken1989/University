package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.ClassForSubjectService;

import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.service.UserService;




@Controller
public class SubjectRegisterController {
	
    @Autowired
    private SubjectService subService;
    
    @Autowired
    private FiledRepository fRepository;
    
    @Autowired
    private StudentClassService studentsubservice;
    
    @Autowired
    private ClassForSubjectService classservice;
    
    @Autowired
    private UserService userservice;

    

	  @GetMapping("/SubjectRegister") 
	  public String SubjectRegister(Model model) 
	    {
		  User user = userservice.getUserByUserEmail("admin");
		  List<SubjectLevel> subjectLevels = subService.listSubjectLevel();
		  List<Field> fields = user.getFields();
		  model.addAttribute("fields",fields);
		  model.addAttribute("subjectlevels",subjectLevels );
	      return "page/RegisterSubject/New"; 
	    }
	  
	  @GetMapping("/listSubjectByStudentAndLevel")
	  @ResponseBody
	  public List<SubjectDto> listSubjectByStudentAndLevel(@RequestParam Long fieldId,@RequestParam Long subjectLevelId) {

	      User  student = userservice.getUserByUserEmail("admin");
	      List<SubjectDto> listSubjects = subService.findByStudent(student, fieldId);
	      List<Subject> subjectsByLevel = subService.findByLevel(subjectLevelId);
	      List<SubjectDto> filteredSubjects = new ArrayList<>();
	      for (SubjectDto subjectDto : listSubjects) {
	          for (Subject subject : subjectsByLevel) {
	              if (subject.getId().equals(subjectDto.getId())) {
	                  filteredSubjects.add(subjectDto);
	                  break;
	              }
	          }
	      }

	      return filteredSubjects;

	
	  }
	  
	    @GetMapping("/listClassForSubject")
	    @ResponseBody
	    public List<ClassForSubjectDto> listClass(@RequestParam Long subjectId)
	    {
	    	// take RegisteringList of student
	    	List<StudentClass> studentClasses = studentsubservice.findSubjectByStudentId(2L);
	    	
	    	
	    	// take list Class of each subject
	    	List<ClassForSubjectDto> listclass = classservice.findById(subjectId);
	        String[][] scheduleTable = addToSchedule(2L);
	    	for(ClassForSubjectDto i: listclass )
	    	{
	    	      int newSlotStart = i.getSlotStart();
	    	      int newSlotEnd = i.getSlotEnd();
	    	      int newWeekday = i.getWeekDay();
	    	      String newSemesterType = i.getStyle();
	    	      // check conflict SLot
		          boolean hasConflict = checkForScheduleConflict(scheduleTable, newSlotStart, newSlotEnd, newWeekday, newSemesterType);
		          i.setConflict(hasConflict);
		         
		          
		          // check conflict subject name ( already have same subject in RegisteringList
		          for(StudentClass s:studentClasses )
		          {
		        	  if(s.getClassforSubject().getSubject().getName().equals(i.getSubject().getName()))
		        			  {
		        		          i.setAlreadyRegis(true);
		        			  }
		        	  else {
		        		      i.setAlreadyRegis(false);
					       }
		          }
		         

	    	}
	    	
	    	return  listclass;
	    	
	    }
	    
	    @GetMapping("/SubjectRegister/Ongoing")
	    public String showSchedule(Model model, Long studentId) {
	        String[][] scheduleTable = addToSchedule(2L);

	        int newSlotStart = 4;
	        int newSlotEnd = 6;
	        int newWeekday = 4;
	        String newSemesterType = "SecondHalfSemester";
	        String newSubjectInfo = "adawdwdawd";
	        boolean hasConflict = checkForScheduleConflict(scheduleTable, newSlotStart, newSlotEnd, newWeekday, newSemesterType);
	        System.out.print("conflictttttttttttttt:"+hasConflict);
	    
	        model.addAttribute("scheduleTable", scheduleTable);

	        return "page/RegisterSubject/schedule";
	    }
	    
	    
	    private boolean checkForScheduleConflict(String[][] scheduleTable, int newSlotStart, int newSlotEnd, int newWeekday, String newSemesterType) {
	        for (int slot = newSlotStart; slot <= newSlotEnd; slot++) {
	            String existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1)]; // Check the first half of the day
	        	if(newSemesterType.equals("SecondHalfSemester"))
	        	{
	        		 existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1) + 1];
	        		    if (existingClass != null && !existingClass.isEmpty()) {
	    	                // Check for conflicts with existing classes
	    	                return true;
	    	            }
	        	}
	        	
	            if (existingClass != null && !existingClass.isEmpty()) {
	                // Check for conflicts with existing classes
	                return true;
	            }
	            if (newSemesterType.equals("FullSemester")) {
	                existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1) + 1]; // Check the second half of the day
	                if (existingClass != null && !existingClass.isEmpty()) {

	                    return true;
	                }
	            }
	        }
	        return false; 
	    }
	    
	    private String[][] addToSchedule(Long studentId)
	    {
	       
	        List<StudentClass> studentClasses = studentsubservice.findSubjectByStudentId(studentId);
	        // Fake data representing schedule table
	        String[][] scheduleTable = new String[12][12];
	        for (StudentClass studentClass : studentClasses) {
	            ClassForSubject classForSubject = studentClass.getClassforSubject();
	            int slotStart = classForSubject.getSlotStart();
	            int slotEnd = classForSubject.getSlotEnd();
	            int weekday = classForSubject.getWeekDay();
	            String semesterType = classForSubject.getStyle();
	            String subjectInfo = classForSubject.getSubject().getName() + " " + classForSubject.getRoom().getName();

	            for (int slot = slotStart; slot <= slotEnd; slot++) {
	                if (semesterType.equals("FullSemester")) {
	                    // Populate both halves of the day
	                    scheduleTable[slot - 1][2 * (weekday - 1)] = subjectInfo;
	                    scheduleTable[slot - 1][2 * (weekday - 1) + 1] = subjectInfo;
	           
	                } else if (semesterType.equals("FirstHalfSemester")) {
	                    // Populate only the first half of the day
	                    scheduleTable[slot - 1][2 * (weekday - 1)] = subjectInfo;

	                } else if (semesterType.equals("SecondHalfSemester")) {
	                    // Populate only the second half of the day
	                    scheduleTable[slot - 1][2 * (weekday - 1) + 1] = subjectInfo;

	                }
	            }
	        }
	        
	        return scheduleTable; 	
	    }
   
}
