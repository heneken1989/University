package com.aptech.group3.Controller;


import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.EmailService;
import com.aptech.group3.serviceImpl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotController {
	Random random = new Random(1000);
	
	@Autowired
	private UserServiceImpl userService;
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private UserRepository uRepo;
	
	@Autowired
	private EmailService emailService;
	//email id form open handler
	@GetMapping("/forgot")
	public String openEmailForm() {
		return "forgot_email_password";
	}

	
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email, HttpSession session, Model model) {
	    System.out.println("Emailllllllll: " + email);

	    // Kiểm tra xem email có tồn tại trong cơ sở dữ liệu hay không
	    User user = userService.findByEmail(email);
	    System.out.println("User::::::::::::::::: " + user);
	    if (user == null) {
	    	model.addAttribute("aaaerrorMessage", "Email does not exist!.");
	        return "forgot_email_password";
	    } else {
	        int otp = random.nextInt(999999);
	        System.out.println("OTPPPP: " + otp);

	        String subject = "OTP Forgot Password";
	        String message = "OTP is:  " + otp;
	        String to = email;

	        //write code for send otp to email..
	        boolean flag = this.emailService.sendEmail(subject, message, to);

	        if (flag) {
	            session.setAttribute("myotp", otp);
	            session.setAttribute("email", email);
	            model.addAttribute("successMessage", "OTP sent successfully!");
	            return "verify_otp";
	        } else {
	        	model.addAttribute("errorMessage", "Failed to send OTP. Please try again.");
	            return "forgot_email_password";
	        }
	    }
	}

    	
 
 
 @PostMapping("/verify-otp")
 public String verifyOtp(@RequestParam("otp") int otp, HttpSession session) {
	 int myOtp = (int)session.getAttribute("myotp");
	 String email = (String)session.getAttribute("email");
	 if(myOtp == otp) {
		 User user = this.uRepo.getUserByUsername(email);
		 System.out.println("User ======: " + user);
		 if(user == null) {
		    	session.setAttribute("message", "User does not exit with this email!");
				return "forgot_email_password";
		 }else {
			 return "change_form";
		 }
		 
	 }else {
		 session.setAttribute("message", "You have entered wrong otp!");
		 return "verify_otp";
	 }
 }
 
 @PostMapping("/change-form")
 public String changePassword(@RequestParam("newpassword") String newPass, HttpSession session,  RedirectAttributes redirectAttributes) {
	 
	 
	 String email = (String)session.getAttribute("email");
	 User user = this.uRepo.getUserByUsername(email);
	 user.setPassword(this.bcrypt.encode(newPass));
	 this.uRepo.save(user);
	 redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully!");
	 return "redirect:/login";
	 
 }
}
