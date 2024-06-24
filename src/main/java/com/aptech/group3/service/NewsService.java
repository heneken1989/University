package com.aptech.group3.service;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.aptech.group3.entity.News;

public interface NewsService {
	 public List<News> getTopNews(int quantity);
	 Page<News> findAll(Pageable pageable);
    Optional<News> findById(Long id);
    void save(News news);
    void deleteById(Long id);
    List<News>findAll();
}