package com.aptech.group3.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.aptech.group3.entity.Holiday;
import com.aptech.group3.entity.RequiredSubject;

public interface RequiredSubjectRepository extends JpaRepository<RequiredSubject, Long> {

           public List<RequiredSubject> findBySubjectId(Long subjectId);
}
