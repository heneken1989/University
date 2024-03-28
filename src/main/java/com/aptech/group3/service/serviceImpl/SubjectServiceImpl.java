package com.aptech.group3.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Repository.SubjectLevelRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.service.SubjectService;

@Service
public class SubjectServiceImpl implements  SubjectService{

	
	  @Autowired
	    private SubjectRepository subjectRepo;
	    @Autowired
	    private SubjectLevelRepository LevelRepo;
	    
	    
	    public List<Subject> searchSubject(String name, Integer fieldId, Integer levelId) {
	    	List<Subject> data =subjectRepo.findByMultipleCriteria(name, levelId, fieldId);
	    	return data;
	    }
	    
	    public List<Subject> getByField(int id){
	    	return subjectRepo.findByFieldId(id);
	    }
	    
	    public List<Subject> findAll(){
	    	return subjectRepo.findAll();
	    }
	    
	    public int getCredit(int id) {
	    	return subjectRepo.getCreditById(id);
	    }
	    
	    
	    public List<SubjectLevel> listSubjectLevel()
	    {
	    	return LevelRepo.findAll();
	    }
	    
	    public List<Subject> findBySubjectName(String name)
	    {
	    	return subjectRepo.findByNameContainingIgnoreCase(name);
	    }
	    
	    public Optional<Subject> findbyId(Long id)
	    {
	    	return subjectRepo.findById(id);
	    }
	    
	    public List<Subject> findByLevel(Long levelId)
	    {
	    	return subjectRepo.findBySubjectlevelId(levelId);
	    }
	    
}
