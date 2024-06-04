package com.aptech.group3.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.Dto.UserStatus;
import com.aptech.group3.entity.User;

import jakarta.transaction.Transactional;


public interface UserRepository extends JpaRepository<User, Long> {
	Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	@Transactional
	@Modifying
	 @Query("update User u SET u.mobileCode = :mobileCode where u.id = :id ")
	    public void updateMobiCode(String mobileCode, Long id);
	
	 @Query("SELECT u FROM User u JOIN u.fields f WHERE u.status = :status AND f.id = :fieldId AND u.role LIKE :role")
	    List<User> findStudentByStatusAndField( UserStatus status,  Long fieldId, String role);
	 
	 @Query("SELECT u FROM User u WHERE u.status =:status AND  u.role LIKE :role ")
	 List<User> getListUserByRoleAndStatus(UserStatus status,  String role);
	//new
	 
    Optional<User> findById(Long id);
    List<User> findByRole(String role);
    //User findByEmail(String email);
    public User findByResetPasswordToken(String token);	
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User getUserByUsername(@Param("email") String email);
    
    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email); 
    public Page<User> findByRole(String role, Pageable pageable); 
    int countStdByRole(String role);
    int countTcByRole(String role);
    int countEmByRole(String role);
    
    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    public void updatePassword(String password, Long id);
    
}