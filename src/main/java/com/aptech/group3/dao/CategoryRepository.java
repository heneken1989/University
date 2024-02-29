package com.aptech.group3.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aptech.group3.entity.Category;






public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Optional<Category> findById(Long id);

}
