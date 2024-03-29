package com.aptech.group3.entity;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String name;
	private String password;
	private String phone;
	private String infomation;
	private String role;
	private String address;
	private String avatar;
	private String resetPasswordToken;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	    name = "user_field", 
	    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
	    inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id")
	)
	  
	@JsonIgnore
	  private List<Field> fields;
	

}