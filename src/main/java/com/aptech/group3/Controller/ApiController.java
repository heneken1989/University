package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.SubjectLevelRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.ExamQuestionAnswer;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizAnswer;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.model.LoginRequest;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.ExamQuestionAnswerService;
import com.aptech.group3.service.QuizAnswerService;
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
    Long quizId = requestBody.get("quizId");
    System.out.print("quizId"+quizId);
  Quiz quiz =quizRepository.getById(quizId);
    List<QuizQuestion>  listqQuestions = quiz.getQuizquestions();
	return listqQuestions ;
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

	List<StudentClass> waitingList = studentsubservice.findEarliestByStatus("WaitingList");
	// if have waitinglist , transfer earliest Student in WaitingList to List , and
	// no need to change quantity
	if (!waitingList.isEmpty()) {
		StudentClass tranStudentClass = waitingList.get(0);
		tranStudentClass.setStatus("List");
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




@GetMapping("/api/AllField")
public List<Field> GetAllField() {
    return filedRepository.findAll() ;
}

@GetMapping("/api/AllLevel")
public List<SubjectLevel> GetAllLevel() {
    return subjectLevelRepository.findAll() ;
}

@GetMapping("/api/listSubjectByLevelAndField/{levelId}/{fieldId}")
public List<Subject> GetAllSubjectByLevelAndField(@PathVariable Long levelId, @PathVariable Long fieldId) {
    return subService.getByFieldAndLevel(levelId, fieldId);
}

@GetMapping("/check/{userId}")
public ResponseEntity<UserDetails> getUserById(@PathVariable Long userId) {
    // Retrieve user information from the UserService based on the user ID
    UserDetails user = userservice.loadUserByUserid(userId);
    if (user != null) {
        // If user information is found, return it with HTTP status 200 OK
        return ResponseEntity.ok(user);
    } else {
        // If user information is not found, return HTTP status 404 Not Found
        return ResponseEntity.notFound().build();
    }
}



@PostMapping("api/loginn")
public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Trả về jwt cho người dùng.
    String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
    return ResponseEntity.ok(jwt);
}


@GetMapping("/auth/csrf-token")
public ResponseEntity<String> getCsrfToken(HttpServletRequest request) {
    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
    System.out.print(csrfToken.getToken());
    return ResponseEntity.ok(csrfToken.getToken());
}


@GetMapping("/api/ClassRegister/list")
public List<Subject> listSubject() {
    return subService.listSubject();
}



@PostMapping("/listClass")
public List<ClassForSubjectDto> listClass(@RequestBody Long id) {
	return classservice.findBySubjectId(id);
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

@PostMapping("/loginThanh")
public Optional<User> loginn(@RequestBody LoginRequest data) {

    return userservice.login(data.email, data.password);
}

}