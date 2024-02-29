package com.aptech.group3.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.dao.CategoryRepository;
import com.aptech.group3.dao.SubjectRepository;
import com.aptech.group3.entity.Category;
import com.aptech.group3.entity.Subject;





@Service
public class SubjectService {
	
    @Autowired
    private SubjectRepository roomRepository;
    @Autowired
    private CategoryRepository cateRepo;
    
    public List<Category> listCate()
    {
    	return cateRepo.findAll();
    }
    
    

    public Subject saveRoom(Subject room) {
    	
    	  // Retrieve the Category object based on the provided category_id
        //Long categoryId = room.getCategory().getId();
     //   Category category = cateRepo.findById(categoryId)
      //      .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        
        // Set the retrieved Category object to the room's category field
    //    room.setCategory(category);
        
        return roomRepository.save(room);
    }
    
    public List<Subject> listRoom()
    {
    	return roomRepository.findAll();
    }
    
    public void deleteRoom(Long id)
    {
    	roomRepository.deleteById(id);
    }
    
    public void updateroom(Subject room)
    {
    	Long RoomId = room.getId();
    	if(roomRepository.existsById(RoomId))
    	{
    		roomRepository.save(room);
    	}
    	
    }
    
    public List<Subject> findRoomByCategoryId(Long categoryName)
    {
    	return roomRepository.findByCategoryId(categoryName);
    }
    
    public List<Subject> findRoomByCategoryIdGreaterThan(Long categoryId)
    {
    	return roomRepository.findByCategoryIdGreaterThan(categoryId);
    }
    
    public List<Subject> findByCategoryIdGreaterThanAndCategoryIdLessThan(Long min,Long max)
    {
    	return roomRepository.findByCategoryIdGreaterThanAndCategoryIdLessThan(min,max);
    }
    
    public List<Subject> findBySubjectName(String name)
    {
    	return roomRepository.findByNameContainingIgnoreCase(name);
    }
    
    public Optional<Subject> findbyId(Long id)
    {
    	return roomRepository.findById(id);
    }
    
    

}
