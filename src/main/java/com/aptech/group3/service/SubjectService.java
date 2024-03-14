package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.dao.SubjectLevelRepository;
import com.aptech.group3.dao.SubjectRepository;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.Subject;





@Service
public class SubjectService {
	
    @Autowired
    private SubjectRepository subjectRepo;
    @Autowired
    private SubjectLevelRepository LevelRepo;
    
    public List<SubjectLevel> listSubjectLevel()
    {
    	return LevelRepo.findAll();
    }
    

    public Subject saveSubject(Subject sub) {
    	
    	  // Retrieve the Category object based on the provided category_id
        //Long categoryId = room.getCategory().getId();
     //   Category category = cateRepo.findById(categoryId)
      //      .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        
        // Set the retrieved Category object to the room's category field
    //    room.setCategory(category);
        
        return subjectRepo.save(sub);
    }
    
    public List<Subject> listSubject()
    {
    	return subjectRepo.findAll();
    }
    
    public void deleteSubject(Long id)
    {
    	subjectRepo.deleteById(id);
    }
    
    public void updateSubject(Subject sub)
    {
    	Long RoomId = sub.getId();
    	if(subjectRepo.existsById(RoomId))
    	{
    		subjectRepo.save(sub);
    	}
    	
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
