package com.aptech.group3.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    //User findByEmail(String email);
    public User findByResetPasswordToken(String token);	
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByUsername(@Param("email") String email);
    
    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email); 
 
    
}