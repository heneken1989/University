package com.aptech.group3.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.group3.entity.User;






@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserid(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
}