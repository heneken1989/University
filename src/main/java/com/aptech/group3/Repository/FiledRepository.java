package com.aptech.group3.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Field;

public interface FiledRepository extends JpaRepository<Field, Long> {
    
}
