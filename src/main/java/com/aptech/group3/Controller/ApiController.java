package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.aptech.group3.entity.Category;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentSubject;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.model.LoginRequest;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.JwtTokenProvider;
import com.aptech.group3.service.StudentSubjectService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import jakarta.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin(origins = "*")
public class ApiController {
	

	    @Autowired
	    private SubjectService roomService;
	    
	    
	    @Autowired
	    AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtTokenProvider tokenProvider;
	    
	    @Autowired
	    private StudentSubjectService studentsubservice;
	    
	    @Autowired
	    private ClassForSubjectService classservice;
	    
	    @Autowired
	    private UserService userservice;
	    
	    
	    @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

	        // Xác thực từ username và password.
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(
	                        loginRequest.getEmail(),
	                        loginRequest.getPassword()
	                )
	        );
	        // Nếu không xảy ra exception tức là thông tin hợp lệ
	        // Set thông tin authentication vào Security Context
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
	    

	    @GetMapping("/list")
	    public List<Subject> listRoom()
	    {
	    	return  roomService.listRoom();
	    }
	    
	    @PostMapping("/listClass")
	    public List<ClassForSubject> listClass(@RequestBody Long id)
	    {
	    	return  classservice.findById(id);
	    }
	    
	    @PostMapping("/listSS")
	    public List<StudentSubject> listSB(@RequestBody Long userid){
	    	return  studentsubservice.findSubjectByStudentId(userid);
	    }
	    

	    
	    
	    @GetMapping("/listcate")
	    public List<Category> listCate()
	    {
	    	return roomService.listCate();
	    }
	    
	    @GetMapping("/jwt")
	    public String jwtchecks()
	    {
	    	return  "JWT Hợp lệ mới có thể thấy được message này";
	    }
	    
	    
	    @PostMapping("/test")
	    public Subject addRoom(@RequestBody Subject room) {
	    	  System.out.println("Request Body: " + room.toString());
	    
	        return roomService.saveRoom(room);
	    }
	    
	    @PostMapping("/delete/{id}")
	    public void deleteRoom(@PathVariable Long id)  // @PathVariable  : parameter id get from URL
	    {
	    	roomService.deleteRoom(id);
	    }
	    
	    @PostMapping("/update/{id}")
	    public void updateRoom(@RequestBody  Subject room)
	    {
	    	 System.out.println("Room with ID " + room.getId() + room.getName() + room.getCredit()  );
	    	roomService.updateroom(room);
	    }
	    
	    @PostMapping("/searchSubject")
	    public ResponseEntity<?> search(@RequestBody String value)
	    {
	 
	
	        // Search Value is Subjet Code ?
	    	if(value.matches("^\\d+$"))
	    	{
	    		Long Lvalue = Long.parseLong(value);
	    		Optional<Subject> supject = roomService.findbyId(Lvalue);
	    		
	    		if(supject!=null)
	    		{
	    			return ResponseEntity.ok(supject);
	    		}
	    		else
	    		{
	    			return ResponseEntity.notFound().build();
	    		}
	    	}
	    	// Search Value is Name of Subject
	    	else
	    	{
	    			List<Subject> supjects = roomService.findBySubjectName(value);
	    	  		if(supjects != null) {
		    			return ResponseEntity.ok(supjects);
		    		}
		    		else
		    		{
		    			return ResponseEntity.notFound().build();
		    		}
	    
	    	}
	    }
	    
	    
	    
	    
	    @PostMapping("/loginThanh")
	    public Optional<User> loginn(@RequestBody LoginRequest  data)
	    {
	    
	    	return userservice.login(data.email, data.password);
	    }
	
}
