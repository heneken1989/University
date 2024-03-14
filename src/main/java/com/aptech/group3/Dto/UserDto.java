package com.aptech.group3.Dto;

import java.math.BigDecimal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class UserDto    {

    private Long id;
	private String email;
	private String name;
	private String password;
	private String phone;
	private String infomation;
	private String role;
	private String address;
	private String avatar;
    private Long field_Id;
	
		


	
	
	


	
	
}