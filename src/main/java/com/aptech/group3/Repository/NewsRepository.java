package com.aptech.group3.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.aptech.group3.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {
	
}