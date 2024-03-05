package com.aptech.group3.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aptech.group3.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    User findByEmail(String email);
}