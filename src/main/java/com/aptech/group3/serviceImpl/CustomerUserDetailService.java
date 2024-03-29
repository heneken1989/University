package com.aptech.group3.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.User;







@Service
public class CustomerUserDetailService implements UserDetailsService {
     
	@Autowired
	UserServiceImpl uservice;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
	     	  User user = userRepository.findByEmail(username);
	     	  
	     	  if(user!=null)
	     	  {

				   System.out.println("aadwawddddddddddd"+user);
				  List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
				  GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
	              grantList.add(authority);
				 
				  UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(), grantList);
				  System.out.println(userDetails);
				  return userDetails;
	     		  
	     	  }
	     	  else {
	           return null;
			}


		

	}
  
}
