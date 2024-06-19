package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aptech.group3.entity.Payment;


public interface PaymentService {
    Optional<Payment> findById(Long id);
    List<Payment> findByStudentId(Long studentId);
    Page<Payment> findAll(Pageable pageable);
    void save(Payment payment);
    
    
}