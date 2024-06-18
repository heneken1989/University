package com.aptech.group3.Controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassStatus;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.ExamQuestionAnswerRepository;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.QuizAnswerRepository;
import com.aptech.group3.Repository.QuizExamRepository;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.SubjectLevelRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.ExamQuestionAnswer;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizAnswer;
import com.aptech.group3.entity.QuizExam;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.model.LoginRequest;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.ExamQuestionAnswerService;
import com.aptech.group3.service.QuizAnswerService;
import com.aptech.group3.service.QuizExamService;
import com.aptech.group3.service.QuizService;
import com.aptech.group3.service.RequiredSubjectService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.serviceImpl.JwtTokenProvider;
import com.aptech.group3.serviceImpl.StudentClassServiceImpl;
import com.aptech.group3.serviceImpl.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")
public class ApiController {
	
	@Autowired
	private ClassForSubjectRepository classForSubjectRepository;
	
@Autowired
private SubjectService subService;

@Autowired
private QuizRepository quizRepository;


@Autowired
private StudentClassRepository studentClassRepository;


@Autowired
AuthenticationManager authenticationManager;

@Autowired
private JwtTokenProvider tokenProvider;

@Autowired
private StudentClassServiceImpl studentsubservice;

@Autowired
private ClassForSubjectService classservice;

@Autowired
private UserServiceImpl userservice;


@Autowired
private FiledRepository filedRepository;

@Autowired
private SubjectLevelRepository subjectLevelRepository;

@Autowired
private QuizAnswerService quizAnswerService;

@Autowired
private ExamQuestionAnswerService examQuestionAnswerService;
@Autowired
private ExamQuestionAnswerRepository examQuestionAnswerRepository;

@Autowired
private QuizQuestionRepository quizQuestionRepository;
@Autowired
private QuizAnswerRepository quizAnswerRepository;

@Autowired
private QuizExamRepository quizExamRepository;

@Autowired
private QuizExamService quizExamService;


@Autowired
private QuizService quizService;

@Autowired
private UserRepository userRepository;

@Autowired
private ClassForSubjectService classForSubjectService;

@Autowired
private RequiredSubjectService requiredSubjectService;



@GetMapping("/api/public/listQuizBySubject")
@ResponseBody
public List<Quiz> showListQuizBySubjectAndTecher(@RequestParam Long classId,@AuthenticationPrincipal CustomUserDetails currentUser ) {
        ClassForSubject currentClass = classForSubjectRepository.getById(classId);
	    return quizService.findListQuizBySubjectIdAndTeacherId(currentClass.getSubject().getId(), currentUser.getUserId(),"Finished");
}


@GetMapping("/api/public/listAlreadyApplied")
@ResponseBody
public List<QuizExam> ListQuizAlreeadyApplied(@RequestParam Long classId,@AuthenticationPrincipal CustomUserDetails currentUser ) {
     
	    return quizExamService.findExamByClassId(classId);
}

@PostMapping("/api/Quiz/Submit")
@ResponseBody
public void QuizSubmit(@RequestBody Map<String, Object> requestBody) {
	
	String typeRequet = requestBody.get("type").toString();
	Long quizExamId = Long.valueOf(requestBody.get("quizExamId").toString());
	System.out.print("quiz exam id"+quizExamId);
    @SuppressWarnings("unchecked")
	Map<String, List<Integer>> answers = (Map<String, List<Integer>>) requestBody.get("answers");
    Long currentQuestionId = null;
    List<Long> answerIds = null;
    
    // Take question Id and answer Id List from client
    for (Map.Entry<String, List<Integer>> entry : answers.entrySet()) {
         currentQuestionId = Long.valueOf(entry.getKey());
         answerIds = entry.getValue().stream()
                 .map(Long::valueOf)
                 .collect(Collectors.toList());
    }
    
    System.out.println("curent question id:" +currentQuestionId);
    
    // after having List answers , save new answers to database ,
        // first delete all old answers
    List<ExamQuestionAnswer> listneedtodelete = examQuestionAnswerService.findByQuizExamIdAndQuestionId(quizExamId, currentQuestionId);  
        for( ExamQuestionAnswer answer : listneedtodelete)
        {
        		 examQuestionAnswerRepository.delete(answer);
        	      
        }
               
  	  QuizExam exam =  quizExamRepository.findById(quizExamId).orElse(null); 	 
	  QuizQuestion question =quizQuestionRepository.findById(currentQuestionId).orElse(null);
	  
    for(Long answerId: answerIds)
    {
    	ExamQuestionAnswer savedAnswer = new ExamQuestionAnswer();
	    QuizAnswer answer = quizAnswerRepository.findById(answerId).orElse(null);
	    savedAnswer.setQuizAnswer(answer);
	    savedAnswer.setQuizExam(exam);
	    savedAnswer.setQuizQuestion(question);
	    examQuestionAnswerRepository.save(savedAnswer);
    }
    
    //canculate mark and update into exam    
    Float markFloat = quizExamService.canculateExamMark(quizExamId);
	exam.setTotalMark(markFloat);
	
	if(typeRequet.equals("submit"))
	{
		exam.setStatus("Submitted");
		
	}
	quizExamRepository.save(exam);
}


@PostMapping("/api/Quiz/StartQuiz")
@ResponseBody
public void StartQuiz(@RequestBody Map<String, Long> requestBody) {
       Long quizExamId = requestBody.get("quizExamId");
       Long studentId = requestBody.get("studentId");
	   QuizExam exam =  quizExamRepository.findById(quizExamId).orElse(null);
	   LocalDateTime now = LocalDateTime.now();
       exam.setStatus("OnProcess"); 
	   exam.setStart_exam_time(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));			   
	   int duration = exam.getQuiz().getDuration();
	   LocalDateTime endDate = now.plusMinutes(duration);
	   exam.setSubmit_exam_time(Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant())); 
	   quizExamRepository.save(exam);

}



@PostMapping("/api/public/Quiz/AutoUpdateQuizExam")
@ResponseBody
public void updateQuiz(@RequestBody Map<String, Long> requestBody,@AuthenticationPrincipal CustomUserDetails currentUser) {
    Long classId = requestBody.get("classId");
	 List<QuizExam> listQuizExam = quizExamService.findExamByStudentIdAndClassId(currentUser.getUserId(), classId);
	    LocalDateTime now = LocalDateTime.now();
	    for(QuizExam aExam : listQuizExam )
	    {
		    Date endTimeDate = aExam.getSubmit_exam_time();
		    LocalDateTime endTime = endTimeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		    if(now.isAfter(endTime))
		    {
		    	aExam.setStatus("Submitted");
		 	   quizExamRepository.save(aExam);
		    } 
	    }


}


@PostMapping("/api/ClassRegister")
@ResponseBody
public Long ClassRegister(@RequestBody Map<String, Long> requestBody) {
    Long classId = requestBody.get("classId");
    Long userId = requestBody.get("userId");
	studentsubservice.RegisterClassMobile(classId, userId);
	return classId;
}


@Transactional
@PostMapping("/api/Quiz")
public List<QuizQuestion> FindQuizById(@RequestBody Map<String, Long> requestBody) {
    Long quizId = requestBody.get("examIDD");
    System.out.println("quizId from client:" + quizId);
    QuizExam exam = quizExamRepository.getById(quizId);
    
     System.out.print("exam Id"+exam.getId());
  Quiz quiz =quizRepository.getById(exam.getQuiz().getId());
  
  System.out.println("quiz Id:"+quiz.getId());
    List<QuizQuestion>  listqQuestions = quiz.getQuizquestions();
    for(QuizQuestion aQuestion :listqQuestions )
    {
    	System.out.println("question id :" + aQuestion.getId());
    }
	return listqQuestions ;
}


@PostMapping("/api/Quiz/ListExam")
public List<QuizExam> FindExamByStudentId(@RequestBody Map<String, Long> requestBody) {
    Long studentId = requestBody.get("studentId");
	LocalDateTime now = LocalDateTime.now();
	Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
	 List<QuizExam> listQuizExam = quizExamService.findExamByStudentIdAndDate(studentId, date);
	return listQuizExam ;
}



@PostMapping("/api/public/Quiz/ListExam")
public List<QuizExam> FindExamByStudentIdWeb(@RequestBody Map<String, Long> requestBody,@AuthenticationPrincipal CustomUserDetails currentUser) {
     Long classId = requestBody.get("classId");
     System.out.print("quizId:"+ classId);
	 List<QuizExam> listQuizExam = quizExamService.findExamByStudentIdAndClassId(currentUser.getUserId(), classId);
	return listQuizExam ;
}



@PostMapping("api/Quiz/QuizExamAnswers")
public List<Long> FindSavedAnswer(@RequestBody Map<String, Long> requestBody) {
    Long questionId = requestBody.get("questionId");
    Long quizExamId = requestBody.get("quizExamId");
    
     List<ExamQuestionAnswer>  listExamQuestionAnswers = examQuestionAnswerService.findByQuizExamIdAndQuestionId(quizExamId, questionId);
     List<Long> answerIdList = new ArrayList<>(); ;
     
        for(ExamQuestionAnswer answer:listExamQuestionAnswers )
        {
        	Long idLong = answer.getQuizAnswer().getId();
        	answerIdList.add(idLong);
        }
   
	return answerIdList ;
}



@Transactional
@PostMapping("/api/Quiz/ListAnswers")
public List<QuizAnswer> FindAnswerByQuestionId(@RequestBody Map<String, Long> requestBody) {
    Long questionId = requestBody.get("questionId");

  List<QuizAnswer> answers = quizAnswerService.findAnswerByQuestionId(questionId);
	return answers ;
}


@PostMapping("/api/Ongoing")
@ResponseBody
public List<StudentClass> ListClassByStudent(@RequestBody Map<String, Long> requestBody) {
	 Long userId = requestBody.get("StudentId");
	List<StudentClass> studentClasses = studentsubservice.findSubjectByStudentId(userId);
	return studentClasses;
}



@PostMapping("/api/cancelClass")
public String CancelClassRegister(@RequestBody Map<String, Long> requestBody) {
	 Long classId = requestBody.get("ClassId");
	StudentClass studentclass = studentClassRepository.getById(classId);
	ClassForSubject classs = studentclass.getClassforSubject();

	List<StudentClass> waitingList = studentsubservice.findEarliestByStatus(ClassStatus.WAITINGLIST);
	// if have waitinglist , transfer earliest Student in WaitingList to List , and
	// no need to change quantity
	if (!waitingList.isEmpty()) {
		StudentClass tranStudentClass = waitingList.get(0);
		tranStudentClass.setStatus(ClassStatus.LIST);
		studentClassRepository.save(tranStudentClass);
	}

	// if have no WaitingList Student , - quantity of class by 1
	else {

		// - quantity of CLass by 1
		int quantity = classs.getQuantity();
		quantity -= 1;
		classs.setQuantity(quantity);
		classForSubjectRepository.save(classs);

	}
	studentClassRepository.delete(studentclass);
	return "redirect:/Ongoing";
}




@GetMapping("/api/public/AllField")
public List<Field> GetAllField() {
    return filedRepository.findAll() ;
}

@GetMapping("/api/public/AllLevel")
public List<SubjectLevel> GetAllLevel() {
    return subjectLevelRepository.findAll() ;
}

@GetMapping("/api/public/listSubjectByLevelAndField/{levelId}/{fieldId}")
public List<Subject> GetAllSubjectByLevelAndField(@PathVariable Long levelId, @PathVariable Long fieldId) {
    return subService.getByFieldAndLevel(levelId, fieldId);
}



@PostMapping("/api/ClassRegister/list")
public List<SubjectDto> listSubject(@RequestBody Map<String, Long> requestBody) {
	Long studentId = requestBody.get("studentId");
	User student = userRepository.getById(studentId);
	List<SubjectDto> listSubjects = new ArrayList<>();
	

	listSubjects=subService.findByStudentMoNeedRequiredSubjectCondition((long) 0);


	List<Subject> subjectsByLevel = new ArrayList<>();
	List<Subject> subjectThatHaveClass = new ArrayList<>();
	
	LocalDateTime now = LocalDateTime.now();
	Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
	List<ClassForSubject> listClassForSubjects = classForSubjectService.findByRegistrationDate(date);
	
	for(ClassForSubject list : listClassForSubjects) 
	{
        
		subjectThatHaveClass.add(list.getSubject());
	}
	
	List<SubjectDto> filteredSubjects = new ArrayList<>();
	for (SubjectDto subjectDto : listSubjects) {
		for (Subject subject : subjectThatHaveClass) {
			if (subject.getId().equals(subjectDto.getId())) {
				filteredSubjects.add(subjectDto);
				break;
			}
		}
	}

	return filteredSubjects;
}



@PostMapping("/listClass")
public List<ClassForSubjectDto> listClass(@RequestBody Map<String, Long> requestBody) {
	Long studentId = requestBody.get("studentId");
	Long subjectId = requestBody.get("subjectId");
	User student = userRepository.getById(studentId);
	// take RegisteringList of student
	List<StudentClass> studentClasses = studentsubservice.findSubjectByStudentId(studentId);
	LocalDateTime now = LocalDateTime.now();
	Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
	// take list Class of each subject
	List<ClassForSubjectDto> listclass = classservice.findBySubjectIdAndDate(subjectId, date);
	String[][] scheduleTable = addToSchedule(studentId, studentClasses);
	List<String> listRequiredSubject = new ArrayList<>();
	List<String> optionalRequiredSubjectList = new ArrayList<>();
	List<RequiredSubject> listreRequiredSubjects = requiredSubjectService.findListRequiredSubjectBySubjectId(subjectId);
	List<Subject> listPassedSupjects = subService.findPassesSubject(student);
	List<String> passesSubjectStrings = new ArrayList<String>();
	
	
	for(Subject aSubject : listPassedSupjects)
	{
		passesSubjectStrings.add(aSubject.getName());
	}
	
	System.out.print(passesSubjectStrings);
	
	for(RequiredSubject a: listreRequiredSubjects)
	{
		  if(a.getStatus().equals("PASS"))
		  {
			  listRequiredSubject.add(a.getRequiredsubject().getName());
		  }
		  else if(a.getStatus().equals("OPTIONAL"))
		  {
			  optionalRequiredSubjectList.add(a.getRequiredsubject().getName());
		  }		
	}
	System.out.print(listRequiredSubject);
	System.out.print(optionalRequiredSubjectList);
	
	  Boolean isPassedAllRequiredSubject = passesSubjectStrings.containsAll(listRequiredSubject);
	  Boolean isPassesOptionalRequiredSubject = false;
	  
	  if(optionalRequiredSubjectList.isEmpty())
	  {
		  isPassesOptionalRequiredSubject = true;
	  }
	  
	  else {
		  
		  for(String a: optionalRequiredSubjectList)
		  {
			  if(passesSubjectStrings.contains(a))
			  {
				  isPassesOptionalRequiredSubject = true;
			  }

		  }
        
	}
	  

		System.out.print("isPassesOptionalRequiredSubject"+isPassesOptionalRequiredSubject);
		System.out.print("isPassedAllRequiredSubject"+isPassedAllRequiredSubject);
	  
	
	for (ClassForSubjectDto i : listclass) {
		int newSlotStart = i.getSlotStart();
		int newSlotEnd = i.getSlotEnd();
		int newWeekday = i.getWeekDay();
		String newSemesterType = i.getType();
		// check conflict SLot
		boolean hasConflict = checkForScheduleConflict(scheduleTable, newSlotStart, newSlotEnd, newWeekday,
				newSemesterType);
		i.setConflict(hasConflict);
		i.setOptionalRequiredSuject(optionalRequiredSubjectList);
		i.setRequiredSubject(listRequiredSubject);
		i.setPassedSubjects(passesSubjectStrings);
		
		// check student match required subject?
		  if(!isPassedAllRequiredSubject || !isPassesOptionalRequiredSubject)
		  {
			  i.setIsHaveRequiredSubject(false);
		  }
		  else {
			  i.setIsHaveRequiredSubject(true);
		       }

		// check conflict subject name ( already have same subject in RegisteringList
		for (StudentClass s : studentClasses) {
			if (s.getClassforSubject().getSubject().getName().equals(i.getSubject().getName())) {
				i.setIsSameSubject(true);
				if (s.getClassforSubject().getId().equals((i.getId()))) {
					i.setIsSameClass(true);
				}
			} else {
				i.setIsSameSubject(false);
				i.setIsSameClass(false);
			}
		}
	}
			
	return listclass;
}
 

@PostMapping("/listSS")
public List<StudentClass> listSB(@RequestBody Long userid){
    return studentsubservice.findSubjectByStudentId(userid);
}





@GetMapping("/api/listcate")
public List<SubjectLevel> listCate() {

    return subService.listSubjectLevel();
}

@GetMapping("/api/jwt")
public String jwtchecks() {
    return "JWT Hợp lệ mới có thể thấy được message này";
}


@PostMapping("/test")
public Subject addRoom(@RequestBody Subject room) {
    System.out.println("Request Body: " + room.toString());

    return subService.saveSubject(room);
}





/*
 * @PostMapping("/searchSubject") public ResponseEntity<?> search(@RequestBody
 * String value) {
 * 
 * 
 * // Search Value is Subjet Code ? if(value.matches("^\\d+$")) { Long Lvalue =
 * Long.parseLong(value); SubjectDto supject = subService.findbyId(Lvalue);
 * 
 * if(supject!=null) { return ResponseEntity.ok(supject); } else { return
 * ResponseEntity.notFound().build(); } } // Search Value is Name of Subject
 * else { List<Subject> supjects = subService.findBySubjectName(value);
 * if(supjects != null) { return ResponseEntity.ok(supjects); } else { return
 * ResponseEntity.notFound().build(); }
 * 
 * } }
 */

private String[][] addToSchedule(Long studentId, List<StudentClass> studentClasses) {

	// Fake data representing schedule table
	String[][] scheduleTable = new String[12][12];
	for (StudentClass studentClass : studentClasses) {
		ClassForSubject classForSubject = studentClass.getClassforSubject();
		int slotStart = classForSubject.getSlotStart();
		int slotEnd = classForSubject.getSlotEnd();
		int weekday = classForSubject.getWeekDay();
		String semesterType = classForSubject.getType();
		String subjectInfo = classForSubject.getSubject().getName() + " " + classForSubject.getRoom().getName();

		for (int slot = slotStart; slot <= slotEnd; slot++) {
			if (semesterType.equals("all")) {
				// Populate both halves of the day
				scheduleTable[slot - 1][2 * (weekday - 1)] = subjectInfo;
				scheduleTable[slot - 1][2 * (weekday - 1) + 1] = subjectInfo;

			} else if (semesterType.equals("fhalf")) {
				// Populate only the first half of the day
				scheduleTable[slot - 1][2 * (weekday - 1)] = subjectInfo;

			} else if (semesterType.equals("lhalf")) {
				// Populate only the second half of the day
				scheduleTable[slot - 1][2 * (weekday - 1) + 1] = subjectInfo;

			}
		}
	}

	return scheduleTable;
}


private boolean checkForScheduleConflict(String[][] scheduleTable, int newSlotStart, int newSlotEnd, int newWeekday,
		String newSemesterType) {
	for (int slot = newSlotStart; slot <= newSlotEnd; slot++) {
		String existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1)]; // Check the first half of the day
		String existingClassSecond = scheduleTable[slot - 1][2 * (newWeekday - 1) + 1];
		if (newSemesterType.equals("lhalf")) {
			if (existingClassSecond != null && !existingClassSecond.isEmpty()) {
				return true;
			}
		}
		if (newSemesterType.equals("fhalf")) {
			if (existingClass != null && !existingClass.isEmpty()) {
				return true;
			}
		}

		if (newSemesterType.equals("all")) {

			if (existingClassSecond != null && !existingClassSecond.isEmpty()) {
				return true;
			}

			existingClass = scheduleTable[slot - 1][2 * (newWeekday - 1)];
			if (existingClass != null && !existingClass.isEmpty()) {
				return true;
			}
		}
	}
	return false;
}

}