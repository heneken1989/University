package com.aptech.group3.Repository;



import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.aptech.group3.entity.Paymenttt;
import com.aptech.group3.entity.StudentClass;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PaymentRepository extends JpaRepository<Paymenttt, Long> {
    List<Paymenttt> findByStudentId(Long studentId);
    Page<Paymenttt> findAll(Pageable pageable);
    
    Page<Paymenttt> findByStudent_CodeContaining(String code, Pageable pageable);
    List<Paymenttt> findByStudentName(@Param("code") String code);
}