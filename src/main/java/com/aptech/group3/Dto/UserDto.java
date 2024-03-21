package com.aptech.group3.Dto;

import com.aptech.group3.entity.Field;

import lombok.Data;


@Data
public class UserDto    {

    private Long id;
	private String email;
	private String name;
	private String phone;
	private String infomation;
	private String role;
	private String address;
	private String avatar;
	private Field field;
	
}