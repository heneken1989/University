package com.aptech.group3.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.group3.dao.UserRepository;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.model.User;





@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user);
    }
    
    public UserDetails loadUserByUserid(Long id) {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUserid(id);
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
    
    



}