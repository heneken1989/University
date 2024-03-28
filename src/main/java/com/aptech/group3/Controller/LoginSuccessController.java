package com.aptech.group3.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.aptech.group3.Dto.UserDto;



@Controller
public class LoginSuccessController {
	
	     
	   @GetMapping("/success")
	    public String loginSuccess() {
	        return "index";
	    }
	   
		@GetMapping(value = "/register")
		public String create(Model model) {
			model.addAttribute("user", new UserDto());
			return "register";
		}


}
