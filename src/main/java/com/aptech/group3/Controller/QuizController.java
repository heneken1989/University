package com.aptech.group3.Controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.group3.Repository.QuizAnswerRepository;
import com.aptech.group3.Repository.QuizQuestionRepository;
import com.aptech.group3.Repository.QuizRepository;
import com.aptech.group3.entity.Quiz;
import com.aptech.group3.entity.QuizQuestion;

import jakarta.transaction.Transactional;

@Controller
public class QuizController {
	
	@Autowired
	private QuizRepository quizRepository;
	private QuizQuestionRepository quizQuestionRepository;
	private QuizAnswerRepository quizAnswerRepository;
          
	@GetMapping("/quiz")
	@Transactional
	public String QuizShow(Model model) {
		List<Quiz> quizlist = quizRepository.findAll();

		model.addAttribute("quizlist",quizlist);
		return "page/Quiz/index";
	}
	
}
