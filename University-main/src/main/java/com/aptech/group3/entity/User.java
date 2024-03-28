package com.aptech.group3.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long id;
	private String email;
	private String name;
	private String password;
	private String phone;
	private String infomation;
	private String role;
	private String address;
	private String avatar;
	
	  @ManyToOne
	  @JoinColumn(name="field_id")
	  private Field field;
	
		


	
	
	


	
	
}