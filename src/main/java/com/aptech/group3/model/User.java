package com.aptech.group3.model;

import java.math.BigDecimal;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User    {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;
	private String email;

	private String name;
	private String username;

	private String password;

	private String phone;


	
	
	


	
	
}