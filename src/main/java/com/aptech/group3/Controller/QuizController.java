package com.aptech.group3.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.unbescape.css.CssIdentifierEscapeLevel;

import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.QuizCreateDto;
import com.aptech.group3.Dto.QuizExamCreateDto;
import com.aptech.group3.Dto.QuizQuestionCreateDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.ExamQuestionAnswerRepository;
import com.aptech.group3.Repository.QuizAnswerRepository;
import com.aptech.group3.Repository.QuizExamRepository;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.ExamQuestionAnswer;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizAnswer;
import com.aptech.group3.entity.QuizClass;
import com.aptech.group3.entity.QuizExam;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.ExamQuestionAnswerService;
import com.aptech.group3.service.QuizAnswerService;
import com.aptech.group3.service.QuizClassService;
import com.aptech.group3.service.QuizExamService;
import com.aptech.group3.service.QuizQuestionService;
import com.aptech.group3.service.QuizService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.SubjectService;
import com.fasterxml.jackson.databind.ObjectMapper;

import groovyjarjarantlr4.v4.parse.ANTLRParser.action_return;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@Controller
public class QuizController {
	
	@Autowired
	private QuizRepository quizRepository;
	@Autowired
	private QuizService quizService;
	@Autowired
	private QuizQuestionRepository quizQuestionRepository;
	@Autowired
	private QuizAnswerRepository quizAnswerRepository;
	
	 @Autowired
	 private QuizQuestionService quizQuestionService;
	 
	 @Autowired
	 private QuizAnswerService quizAnswerService;
	 
	 
	 @Autowired
	 private QuizExamRepository quizExamRepository;
	 
	 @Autowired
	 private ExamQuestionAnswerRepository examQuestionAnswerRepository;
	 
	 @Autowired
	 private ExamQuestionAnswerService examQuestionAnswerService;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private QuizExamService quizExamService;
	 
	 @Autowired
	 private StudentClassService studentClassService;
	 
	 @Autowired
	 private QuizClassService quizClassService;
	 
	 @Autowired
	 private ClassForSubjectService classForSubjectService;
	 
	 @Autowired
	 private ClassForSubjectRepository classForSubjectRepository;
	 
	 @Autowired
	 private SubjectService subjectService;
	 

          
	@GetMapping("/quiz")
	@Transactional
	public String QuizShow(Model model,@RequestParam(defaultValue = "0") int page,RedirectAttributes redirectAttributes,HttpSession session,@RequestParam Long quizId,@AuthenticationPrincipal CustomUserDetails currentUser) {

		   int pageSize = 1;
		   Quiz quiz = quizRepository.getById(quizId);
		   Page<QuizQuestion> questionPage = quizQuestionService.findPaginatedQuestionsByQuizId(quizId, page, pageSize);	 		
		   QuizExam quizExam = quizExamService.findExamByStudentIdAndQuizId(currentUser.getUserId(), quizId);
		   
		    LocalDateTime now = LocalDateTime.now();
		    
		    Date endTimeDate = quizExam.getEndDate();
		   // System.out.print("endTimeDate:"+endTimeDate);
		         if(endTimeDate!=null)
		         {
		 		    LocalDateTime endTime = endTimeDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

				    if(now.isAfter(endTime))
				    {
				    	 redirectAttributes.addFlashAttribute("errorMessage", "The quiz has ended.");
				    	 return "redirect:/listQuiz";
				    } 
		         }
	       model.addAttribute("duration", quiz.getDuration());	   
		   model.addAttribute("quizQuestions", questionPage.getContent());	   
	       model.addAttribute("currentPage", page);
	       model.addAttribute("totalPages", questionPage.getTotalPages());	   	       
	       // Set Time Start exam  and Time end		  
			   quizExam.setStartDate(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));			   
			   int duration = quiz.getDuration();
			   LocalDateTime endDate = now.plusMinutes(duration);
			   quizExam.setEndDate(Date.from(endDate.atZone(ZoneId.systemDefault()).toInstant()));
			   quizExamRepository.save(quizExam);
			   model.addAttribute("quizExamId",quizExam.getId() );			   
		// Set Answers 	   
		  List<ExamQuestionAnswer>  listexamExamQuestionAnswers = examQuestionAnswerService.findByExamID(quizExam.getId());
		  Map<Long, Set<Long>> savedAnswerss = new HashMap<>();
		    for (ExamQuestionAnswer examQuestionAnswer : listexamExamQuestionAnswers) {
		    	   Long questionId = examQuestionAnswer.getQuizQuestion().getId();
		    	    Long answerId = examQuestionAnswer.getQuizAnswer().getId();

		        if (savedAnswerss.containsKey(questionId)) {
		        	savedAnswerss.get(questionId).add(answerId);
		        } else {	
		            Set<Long> answerIds = new HashSet<>();
		            answerIds.add(answerId);
		            savedAnswerss.put(questionId, answerIds);
		        }
		    }		    		   	 
	     	model.addAttribute("savedAnswers", savedAnswerss);
	    
		return "page/Quiz/index";
	}
	
	
	@GetMapping("/result")
	public String showResult(Model model,@AuthenticationPrincipal CustomUserDetails currentUser,@RequestParam  Long quizId) {

		QuizExam exam = quizExamService.findExamByStudentIdAndQuizId(currentUser.getUserId(),quizId);
		model.addAttribute("exam", exam);
		return "/page/Quiz/Result";
	}
	
	@GetMapping("/listQuiz")
	@Transactional
	public String showListQuiz(Model model,@AuthenticationPrincipal CustomUserDetails currentUser) {
           
		List<StudentClass> listClass = studentClassService.findSubjectByStudentId(currentUser.getUserId());

			System.out.print("listClass"+listClass.get(0).getId());
		model.addAttribute("listClass",listClass);
		return "/page/Quiz/QuizList";
	}
	
	
	@GetMapping("/listQuizByClass")
	@ResponseBody
	public List<QuizExam> showListQuizByClass(@RequestParam Long classId,@AuthenticationPrincipal CustomUserDetails currentUser ) {
	         
		return  quizExamService.findExamByStudentIdAndClassId(currentUser.getUserId(), classId)	;
	}
	
	@PostMapping("/submitQuiz")
	public String handleQuizAction(HttpServletRequest request,
	                               @RequestParam String action, @RequestParam String questionIds,@RequestParam Long quizId,@RequestParam Long quizExamId,
	                               HttpSession session) {

	         Map<Integer, Set<String>> answersMap = new HashMap<>();
	         Integer a = Integer.parseInt(questionIds);
	         answersMap.put(a, new HashSet<>());
	         Enumeration<String> paramNames = request.getParameterNames();
			 QuizExam exam =  quizExamRepository.findById(quizExamId).orElse(null);	         
             Long questionIdLong = Long.parseLong(questionIds);
             List<ExamQuestionAnswer> listneedtodelete = examQuestionAnswerService.findByQuestionID(questionIdLong);
              for(ExamQuestionAnswer aa :listneedtodelete )
              {
             	 examQuestionAnswerRepository.delete(aa);
              }            
	        while (paramNames.hasMoreElements()) {
	            String paramName = paramNames.nextElement();
	            if (paramName.startsWith("answers_")) {
	                System.out.println("selected answaer" +request.getParameterValues(paramName));
	                String[] selectedAnswers = request.getParameterValues(paramName);
	                Integer questionId = Integer.parseInt(paramName.substring("answers_".length()));
	                answersMap.put(questionId, new HashSet<>(Arrays.asList(selectedAnswers)));
	      
					  for (String answerId : selectedAnswers) 
					  { 
			
					  Long answerIdLong =Long.parseLong(answerId);
					  QuizQuestion question =quizQuestionRepository.findById(questionIdLong).orElse(null);
					  QuizAnswer answer = quizAnswerRepository.findById(answerIdLong).orElse(null);
					  if (question != null && answer != null) {
						  ExamQuestionAnswer questionAnswer = new ExamQuestionAnswer();				
						  questionAnswer.setQuizExam(exam);
						  questionAnswer.setQuizAnswer(answer);
					      questionAnswer.setQuizQuestion(question);
					      examQuestionAnswerRepository.save(questionAnswer); 
					      } 
					  }					 
	            }	 
	        }	        

	        if ("submitQuiz".equals(action)) {
	        	List<ExamQuestionAnswer> listExamQuestionAnswer = examQuestionAnswerService.findByExamID(quizExamId);
	        	   Float mark = (float) 0;
	        	for(ExamQuestionAnswer l :listExamQuestionAnswer )
	        	{
	        	   Float maxMarkPerQuestion = l.getQuizQuestion().getMark();
	        	
	        	   List<ExamQuestionAnswer> ListAnswers = examQuestionAnswerService.findByQuestionID(l.getQuizQuestion().getId());
	        	   if(l.getQuizQuestion().getType().equals("Single"))
	        	   {
	        	
	        		      int correct = ListAnswers.get(0).getQuizAnswer().getIsTrue();
	        		      if(correct == 1)
	        		      {
	        		    	  mark += maxMarkPerQuestion;
	        		      }
	        	   }
	        	   else
	        	   {
	        		   List<QuizAnswer> listtOriginAnswers = quizAnswerService.findAnswerByQuestionId(l.getQuizQuestion().getId());
	        		   int countOrigin = 0;
	        		   int countAnswer = 0;
	        		   for(QuizAnswer aa:listtOriginAnswers)
	        		   {
	        			   if(aa.getIsTrue() == 1)
	        			   {
	        				   countOrigin += 1;
	        			   }
	        		   }
	        		   float markEachAnswer = (float)(maxMarkPerQuestion/countOrigin);	       
	        		   for(ExamQuestionAnswer bb: ListAnswers )
	        		   {
	        			   if(bb.getQuizAnswer().getIsTrue()==1)
	        			   {
	        				   countAnswer +=1;
	        			   }
	        		   }
	        		   if((ListAnswers.size()-countAnswer) >= countAnswer ) 
	        		        {
						       mark +=0;
				          	}
	        		   if((ListAnswers.size()-countAnswer) < countAnswer)
				          		
				          	{
	        			       int totalCorrectAnswer = (countAnswer - (ListAnswers.size()-countAnswer));
				          		mark += totalCorrectAnswer*markEachAnswer/(ListAnswers.size())   ;

	                    	  }
	        	     }
	        	}	        	
	        	exam.setTotalMark(mark);
	        	exam.setStatus("Submitted");
	        	quizExamRepository.save(exam);	
	         session.removeAttribute("currentPage");
	         session.removeAttribute("totalPages");
	         
	         return "redirect:/result?quizId=" + quizId;
		    } else {
	        int nextPage = determineNextPage(action, getCurrentPage(session));
	        session.setAttribute("currentPage", nextPage);
	        return "redirect:/quiz?page=" + nextPage+"&quizId=" + quizId;
	    }
	}
	
	private int getCurrentPage(HttpSession session) {
	    // Assuming "currentPage" is stored in the session
	    Integer currentPage = (Integer) session.getAttribute("currentPage");
	    if (currentPage == null) {
	        return 0; // Default to the first page
	    }
	    return currentPage;
	}	
	private int determineNextPage(String action, int currentPage) {
	    if ("next".equals(action)) {
	        return currentPage + 1;
	    } else if ("previous".equals(action)) {
	        return Math.max(currentPage - 1, 0); // Ensure we don't go below 0
	    }
	    return currentPage; 
	}
	
	
	@GetMapping("/quiz/create")
	public String create(Model model) {
		QuizCreateDto quiz = new QuizCreateDto();
		model.addAttribute("quiz",quiz);
		return "page/Quiz/create";
	}
	
	
	@GetMapping("/quiz/createQuestion")
	public String createQuestion(Model model,@RequestParam Long quizId) {
	     List<QuizQuestion> listQuestions = quizQuestionService.findListQuestionByQuizId(quizId);	     
	     float currentMark = quizQuestionService.findCurrentMarkOfQuiz(quizId);
	     Quiz quiz =quizRepository.getById(quizId);
	     model.addAttribute("quizQuestions",listQuestions);
		 model.addAttribute("quiz",quiz);
		 model.addAttribute("quizId",quizId);
		 model.addAttribute("currentMark",currentMark);
		return  "page/Quiz/Question";
	}
	
	
	@PostMapping("/quiz/create")
	public String create(Model model, @ModelAttribute("quiz") @Valid QuizCreateDto quiz,BindingResult bindingResult,@AuthenticationPrincipal CustomUserDetails currentUser,@RequestParam(required = false) Long updateId
			) 
	    {
		if (bindingResult.hasErrors())
		{
		
		
			model.addAttribute("quiz",quiz);
			return "page/Quiz/create";
		}
		
		else
		{
			if(updateId!=null)
			{
				 quizService.update(quiz, updateId);
				 model.addAttribute("quizId",updateId);
				 return "redirect:/quiz/createQuestion?quizId=" + updateId;
			}
			 Quiz createdQuiz = quizService.create(quiz,currentUser.getUserId());
			 model.addAttribute("quizId",createdQuiz.getId());
			 return "redirect:/quiz/createQuestion?quizId=" + createdQuiz.getId();
			
		}
	}
	
	@GetMapping("/quiz/questionDetail")
	public String CreateQuestionDetail(@RequestParam("quizId") Long quizId, Model model) {
	    model.addAttribute("quizId", quizId);
	    QuizQuestionCreateDto questionCreateDto = new QuizQuestionCreateDto();
	    model.addAttribute("questionDto",questionCreateDto);	    
	    return "page/quiz/QuestionDetailCreate";
	}
	
	@PostMapping("/quiz/createQuestion")
	public String createQuestion(@ModelAttribute("questionDto") @Valid QuizQuestionCreateDto questionDto,BindingResult bindingResult,@RequestParam("quizId") Long quizId,
			Model model,RedirectAttributes redirectAttributes,@RequestParam(required = false) Long questionId) {
		     questionDto.setQuizId(quizId);
		     
		 	if (bindingResult.hasErrors())
			{
				model.addAttribute("questionDto",questionDto);
				model.addAttribute("quizId", quizId);
	     	    model.addAttribute("correctAnswersJson", convertToJson(questionDto.getCorrectAnswers()));
	        	model.addAttribute("incorrectAnswersJson", convertToJson(questionDto.getIncorrectAnswers()));
				   return "page/quiz/QuestionDetailCreate";
			}
		 	else {
		 	     float currentMark = quizQuestionService.findCurrentMarkOfQuiz(quizId);
			     float possibleMark =quizRepository.getById(quizId).getTotalMark()- currentMark;
			     System.out.print("possible mark: " + possibleMark );
			     System.out.print("curent mark: " +  currentMark);
			
			     if(questionDto.getMark() > possibleMark)
			     {
			    	    model.addAttribute("quizId", quizId);	   	  
			   	        model.addAttribute("questionDto",questionDto);	
			     	    model.addAttribute("errorMessage", "The mark cannot exceed the possible maximum mark.");
			     	    model.addAttribute("correctAnswersJson", convertToJson(questionDto.getCorrectAnswers()));
			        	model.addAttribute("incorrectAnswersJson", convertToJson(questionDto.getIncorrectAnswers()));
			    	     return "page/quiz/QuestionDetailCreate";
			     }
			     
			
		 		
		     QuizQuestion question;
		     
		     if(questionId!= null)
		     {
		    	 question = quizQuestionRepository.getById(questionId);
		       List<QuizAnswer> listAnswers = quizAnswerService.findAnswerByQuestionId(questionId);
		       if(!listAnswers.isEmpty())
		       {
		    	   for(QuizAnswer answer :listAnswers )
		    	   {
		    		   quizAnswerRepository.delete(answer);
		    	   }
		       }
		        question.setContent(questionDto.getContent());
		        question.setMark(questionDto.getMark());
		        question.setType(questionDto.getType());	 
		        quizQuestionRepository.save(question);
		     }
		     else {
		    	 question   =quizQuestionService.create(questionDto);
			 }
		     
		
		     // create answer list
		     if(question!=null) 
		     {  
		  
		    	 List<String> listCorrects= questionDto.getCorrectAnswers();
		    	 for(String answer:listCorrects)
		    	 {
		
		        	 QuizAnswer quizAnswer  = new QuizAnswer();
		        	 quizAnswer.setIsTrue(1);
		        	 quizAnswer.setContent(answer);
		        	 quizAnswer.setQuizQuestion(question);
		        	 quizAnswerRepository.save(quizAnswer);
		        			
		    	 }
		    	 
		    	 List<String> listIncorrects= questionDto.getIncorrectAnswers();
		    	 for(String answer:listIncorrects)
		    	 {
		        	 QuizAnswer quizAnswer  = new QuizAnswer();
		        	 quizAnswer.setIsTrue(0);
		        	 quizAnswer.setContent(answer);
		        	 quizAnswer.setQuizQuestion(question);		
		        	 quizAnswerRepository.save(quizAnswer);
		    	 }
		     }				
		 		 return "redirect:/quiz/createQuestion?quizId=" + quizId;
			}		  	
	}
	
	@GetMapping("/question/delete/{id}")
	public String deleteQuestion(@PathVariable Long id)
	{
		    QuizQuestion question = quizQuestionRepository.getById(id);
		    Long quizId=  question.getQuiz().getId();
			quizQuestionRepository.delete(question);
			return "redirect:/quiz/createQuestion?quizId=" + quizId ;
	}
	
	public String convertToJson(Object object) {
	    try {
	        ObjectMapper mapper = new ObjectMapper();
	        return mapper.writeValueAsString(object);
	    } catch (Exception e) {
	        // Handle exception
	        return "[]";
	    }
	}
	
	
	
	@GetMapping("/quiz/publish/{quizId}")
	public String QuizPublish(@PathVariable Long quizId,Model model,RedirectAttributes redirectAttributes)
	{

		   
		 Quiz quiz = quizRepository.getById(quizId);
		 float currentMark = quizQuestionService.findCurrentMarkOfQuiz(quizId);
		 if(currentMark < quiz.getTotalMark())
		 {
			    String errorMessage = "Cant Publish This Quiz.Total Question Points Must Equal  " + quiz.getTotalMark() + "Points. Current Is " + currentMark + "Point(s).";
			    redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			    return "redirect:/quiz/createQuestion?quizId=" + quizId;
		 }
		 return "redirect:/teacherQuizManage";
		
			
	}
	

	// teacher quiz managerment	
	@GetMapping("/teacherQuizManage")
	public String teacherQuizManage(Model model,@AuthenticationPrincipal CustomUserDetails currentUser) {     
		List<ClassForSubject> listClass = classForSubjectService.findByTeacherId(currentUser.getUserId());			
		model.addAttribute("listClass",listClass);
		return "/page/Quiz/TeacherQuizManage";
	}
	
	@GetMapping("/api/listQuizBySubject")
	@ResponseBody
	public List<Quiz> showListQuizBySubjectAndTecher(@RequestParam Long classId,@AuthenticationPrincipal CustomUserDetails currentUser ) {
	        ClassForSubject currentClass = classForSubjectRepository.getById(classId);
		    return quizService.findListQuizBySubjectIdAndTeacherId(currentClass.getSubject().getId(), currentUser.getUserId());
	}
	
	     @PostMapping("/ApplyQuiz")
	     @ResponseBody
	     public QuizExamCreateDto applyQuiz(
	    		 @RequestBody QuizExamCreateDto dto,
	             @AuthenticationPrincipal CustomUserDetails currentUser,
	             Model model) {

	        System.out.print("Received Start Date: " + dto.getStartDate());
	        ClassForSubject classForSubject = classForSubjectRepository.getById(dto.getClassId());
	        Quiz quiz = quizRepository.getById(dto.getQuizId());
	        List<StudentClass> listStudent = studentClassService.findByClassForSubjectId(dto.getClassId());
	        for (StudentClass studentClass : listStudent) {
	            QuizExam exam = new QuizExam();
	            exam.setTotalMark(0);
	            exam.setStudent(studentClass.getStudent());
	            exam.setQuiz(quiz);
	            exam.setClassForSubject(classForSubject);	                  
	            exam.setStartFromDate(Date.from(dto.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
	            exam.setCloseDate(Date.from(dto.getEndDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
	            quizExamRepository.save(exam);
	        }
	        return dto;
	    }
	     
	     
	     @GetMapping("/quiz/QuizManage")
	     public String ViewListQuiz(Model model,@AuthenticationPrincipal CustomUserDetails currentUser,@RequestParam(name = "page", defaultValue = "1") int page) {
	    	
	 		  Pageable paging = PageRequest.of(page - 1, 5);
	 		 Page<Quiz> listQuiz = quizService.findListQuizByTeacherId(currentUser.getUserId(), paging);
	    	 model.addAttribute("data",listQuiz);
	     	return  "page/Quiz/QuizManage";
	     }
	     
	     
	     @GetMapping("/quiz/update/{id}")
	     public String viewUpdate(Model model,@AuthenticationPrincipal CustomUserDetails currentUser,@PathVariable Long id) {
	    	 QuizCreateDto dto = quizService.findQuizCreateDtoByID(id);
	    	 model.addAttribute("updateId",id);
	    	 model.addAttribute("quiz",dto);
	     	return  "page/Quiz/updateQuiz";
	     }
	     
	     
	     @GetMapping("/quiz/updateQuestion/{id}")
	     public String viewUpdateQuestion(Model model,@AuthenticationPrincipal CustomUserDetails currentUser,@PathVariable Long id) {
	 	    //model.addAttribute("quizId", quizId);
	
	    	    
		    QuizQuestion question = quizQuestionRepository.getById(id);
		    List<QuizAnswer> listAnswers = quizAnswerService.findAnswerByQuestionId(id);
		    QuizQuestionCreateDto dto = new QuizQuestionCreateDto();
		    
		    List<String> correctAnswers = new ArrayList<>();
		    List<String> incorrectAnswers = new ArrayList<>();

		    for (QuizAnswer answer : listAnswers) {
		        if (answer.getIsTrue() == 1) {
		            correctAnswers.add(answer.getContent());
		        } else {
		            incorrectAnswers.add(answer.getContent());
		        }
		    }
		    dto.setCorrectAnswers(correctAnswers);
		    dto.setIncorrectAnswers(incorrectAnswers);
		    dto.setContent(question.getContent());
		    dto.setMark(question.getMark());
		    dto.setType(question.getType());
		    
		          
     	         model.addAttribute("correctAnswersJson", convertToJson(dto.getCorrectAnswers()));
	        	 model.addAttribute("incorrectAnswersJson", convertToJson(dto.getIncorrectAnswers()));
	        	 model.addAttribute("quizId", question.getQuiz().getId());
	        	 model.addAttribute("questionId",id);

		    model.addAttribute("questionDto",dto);	
	     	return  "page/Quiz/QuestionDetailCreate";
	     }
  
	}
	
	
	
	
	
	
	

