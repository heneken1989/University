package com.aptech.group3.service;


import java.util.List;
import java.util.Optional;
import com.aptech.group3.entity.News;

public interface NewsService {
    List<News> findAll();
    Optional<News> findById(Long id);
    void save(News news);
    void deleteById(Long id);
}