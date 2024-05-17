package com.aptech.group3.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResultDto {
	private Long user_id;
	private String name;
	private String avatar;
	private String role;
	private RefreshDto token;
	
}
