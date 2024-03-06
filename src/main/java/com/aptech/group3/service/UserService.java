package com.aptech.group3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.group3.dao.UserRepository;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;






@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetails(user);
    }
    
    public UserDetails loadUserByUserid(Long id) {
        // Kiểm tra xem user có tồn tại trong database không?
        Optional<User> user = userRepository.findById(id);
        if (user == null) {
            return null;
        }
        return new CustomUserDetails(user);
    }
    
    
    public UserDetails loadUserByUserEmail(String email) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetails(user);
    }
    
    
    public User getUserByUserEmail(String email) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return user;
    }
    
    public Optional<User> login(String email,String password)
    {
    	System.out.print("emailllllllllllllll"+email);
    	User user = userRepository.findByEmail(email);
    	System.out.print("aaaaaaaaaaaaaaaa"+user);
    	
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        else
        {
        	return null;
        }
    	
    	
    }
    
    



}