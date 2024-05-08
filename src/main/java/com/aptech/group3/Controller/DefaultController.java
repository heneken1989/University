package com.aptech.group3.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.group3.model.CustomUserDetails;

@Controller

public class DefaultController {

//	@PreAuthorize("hasAuthority('STUDENT')")
    @RequestMapping({"/","/index"})
    
    public String index(Model vari ,@AuthenticationPrincipal CustomUserDetails currentUser)
     { 
		System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaa"+currentUser.getUserId());
		System.out.print("aaaaaaaaaaaaaaaaaaaaaaaaaa"+currentUser.getAuthorities());
    	String variable ="Hello from Uni";
    	vari.addAttribute("data",variable);
    	
    	return "page/test";
     }
    
    @RequestMapping({"/signup"})
    public String sigin()
     { 
    	return "signup";
     }
    
    
    
    @RequestMapping({"/login"})
    public String login()
     { 
 
    	return "login";
     }
    
    

      

     

}
