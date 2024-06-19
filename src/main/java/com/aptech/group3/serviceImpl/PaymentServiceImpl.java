package com.aptech.group3.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.aptech.group3.Repository.PaymentRepository;
import com.aptech.group3.entity.News;
import com.aptech.group3.entity.Payment;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    public PaymentRepository repo;

    public Optional<Payment> findById(Long id) {
        return repo.findById(id);
    }
    @Override
    public List<Payment> findByStudentId(Long studentId) {
        return repo.findByStudentId(studentId);
    }
    @Override
    public Page<Payment> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
   
    @Override 
    public void save(Payment payment)
    {
    	 repo.save(payment);
    }
    
}