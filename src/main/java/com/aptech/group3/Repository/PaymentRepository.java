package com.aptech.group3.Repository;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentId(Long studentId);
    Page<Payment> findAll(Pageable pageable);
}