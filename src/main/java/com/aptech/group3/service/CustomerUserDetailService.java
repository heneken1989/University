package com.aptech.group3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.group3.entity.User;







@Service
public class CustomerUserDetailService implements UserDetailsService {
     
	@Autowired
	UserService uservice;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<User> listUser = (List<User>) uservice.loadUserByUserEmail(username);
		 if(listUser.size()>0)
		  {
			   User user =listUser.get(0);
			  List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
			  GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
              grantList.add(authority);
			 
			  UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantList);
			  System.out.println(userDetails);
			  return userDetails;
		  }
		 else 
			 {
				 new UsernameNotFoundException("LoginFail");
				 
			 }
		
		return null;
	}
  
}
