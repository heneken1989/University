package com.aptech.group3.Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.aptech.group3.Repository.ExamQuestionAnswerRepository;
import com.aptech.group3.Repository.QuizAnswerRepository;
import com.aptech.group3.Repository.QuizExamRepository;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ExamQuestionAnswer;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizAnswer;
import com.aptech.group3.entity.QuizExam;
import com.aptech.group3.entity.QuizQuestion;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.ExamQuestionAnswerService;
import com.aptech.group3.service.QuizAnswerService;
import com.aptech.group3.service.QuizExamService;
import com.aptech.group3.service.QuizQuestionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Controller
public class QuizController {
	
	@Autowired
	private QuizRepository quizRepository;
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
          
	@GetMapping("/quiz")
	@Transactional
	public String QuizShow(Model model,@RequestParam(defaultValue = "0") int page,HttpSession session) {
		int pageSize = 1;
		  Page<QuizQuestion> questionPage = quizQuestionService.findPaginatedQuestionsByQuizId(1L, page, pageSize);
		  List<Quiz> quizlist = quizRepository.findAll();		 
		  List<ExamQuestionAnswer>  listexamExamQuestionAnswers = examQuestionAnswerService.findByExamID(2L);
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
		    
		    System.out.print("aaaaaaaddwdwdwdwd"+savedAnswerss);		   
	        model.addAttribute("quizQuestions", questionPage.getContent());
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", questionPage.getTotalPages());
	     	model.addAttribute("quizlist",quizlist);
	     	model.addAttribute("savedAnswers", savedAnswerss);

		return "page/Quiz/index";
	}
	
	
	@GetMapping("/result")
	public String showResult(Model model,@AuthenticationPrincipal UserDetails currentUser) {
		User u = userRepository.findByEmail(currentUser.getUsername());
		QuizExam exam = quizExamService.findExamByStudentId(u.getId());
		model.addAttribute("exam", exam);
		return "/page/Quiz/Result";
	}
	
	
	
	@GetMapping("/listQuiz")
	public String showListQuiz(Model model,@AuthenticationPrincipal UserDetails currentUser) {
	
		return "/page/Quiz/QuizList";
	}
	
	
	@PostMapping("/submitQuiz")
	public String handleQuizAction(HttpServletRequest request,
	                               @RequestParam String action, @RequestParam String questionIds,
	                               HttpSession session) {

	         Map<Integer, Set<String>> answersMap = new HashMap<>();
	         Integer a = Integer.parseInt(questionIds);
	         answersMap.put(a, new HashSet<>());
	         Enumeration<String> paramNames = request.getParameterNames();
			  QuizExam exam =  quizExamRepository.findById(2L).orElse(null);
	         
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
	        	List<ExamQuestionAnswer> listExamQuestionAnswer = examQuestionAnswerService.findByExamID(2L);
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
	        		   System.out.print("mark each answer:"+ markEachAnswer );
	        		   for(ExamQuestionAnswer bb: ListAnswers )
	        		   {
	        			   if(bb.getQuizAnswer().getIsTrue()==1)
	        			   {
	        				   countAnswer +=1;
	        			   }
	        		   }
	        		   
	        		   System.out.print("count :"+ countAnswer + countOrigin );
	        
	        		   
	        		   if((ListAnswers.size()-countAnswer) >= countAnswer ) 
	        		        {
						       mark +=0;
				          	}
	        		   if((ListAnswers.size()-countAnswer) < countAnswer)
				          		
				          	{
	        			       int totalCorrectAnswer = (countAnswer - (ListAnswers.size()-countAnswer));
				          		mark += totalCorrectAnswer*markEachAnswer/(ListAnswers.size())   ;
				          	   System.out.print("countAnswer :" + countAnswer + "ListAnswers.size() :"+ ListAnswers.size() );
				         	   System.out.print("totalCorrectAnswer :"+ totalCorrectAnswer);
	                    	  }
	        	     }
	        	}
	        	
	        	exam.setTotalMark(mark);
	        	quizExamRepository.save(exam);
	        	
	        	
	        	
	        	System.out.print("markkkkkkkkkkkkkkk:  " + mark);
	         session.removeAttribute("currentPage");
	         session.removeAttribute("totalPages");
		        return "redirect:/result";
		    } else {
	        int nextPage = determineNextPage(action, getCurrentPage(session));
	        session.setAttribute("currentPage", nextPage);
	        return "redirect:/quiz?page=" + nextPage;
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
}
