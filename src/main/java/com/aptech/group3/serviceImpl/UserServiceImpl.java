package com.aptech.group3.serviceImpl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;






@Service
public class UserServiceImpl implements UserDetailsService {

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
        System.out.print("aaaaaaaaaaaaaaa"+id);
        // Kiểm tra xem user có tồn tại trong database không?
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get(); // Retrieve the User object from Optional
            CustomUserDetails userDetails = new CustomUserDetails(user);
            
            return userDetails;
        } else {
            System.out.println("User not found!");
            return null;
        }
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