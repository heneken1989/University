package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;
import com.aptech.group3.entity.Payment;

public interface PaymentService {
    Optional<Payment> findById(Long id);
    List<Payment> findByStudentId(Long studentId);
    List<Payment> findAll();
    void save(Payment payment);
}