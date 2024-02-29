package com.aptech.group3.Controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @RequestMapping({"/","/index"})
    public String index()
     { 
    	return "index";
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
