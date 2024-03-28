package com.aptech.group3.serviceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CurrentTimestamp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.StudentClassService;






@Service
public class StudentClassServiceImpl implements StudentClassService  {
	
	@Autowired
	private ClassForSubjectRepository classForSubjectRepository ;
	
	@Autowired
	private StudentClassRepository repo ;
	
	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	private ModelMapper mapper ;
	
    public static Date convertToDateViaInstant(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
	
	
	public List<StudentClass> findSubjectByStudentId(Long studentId)
	{
		return repo.findByStudentId(studentId);
	}
	
	public List<StudentClass> findEarliestByStatus(String status)
	{
		return repo.findEarliestByStatus(status);
	}
	
	
	public void RegisterClass(ClassForSubjectDto dto, Long userId)
	{
		 ClassForSubject classForSubject = classForSubjectRepository.getById(dto.getId());
		 StudentClass stuclass = new StudentClass();
		 stuclass.setClassforSubject(classForSubject);
		 User u = userRepository.getById(userId);
		 
		 
		 // inscrea current quantity of class +1
		 int quantity = classForSubject.getQuantity();
		 int maxquantity= classForSubject.getMaxQuantity();
		 if(quantity >= maxquantity )
		 {
			 stuclass.setStatus("WaitingList");
		 }
		 else 
			 {
				 quantity += 1;
				 classForSubject.setQuantity(quantity);
				 classForSubjectRepository.save(classForSubject);
				 stuclass.setStatus("List");
				 
			 }
		 LocalDateTime nowDate = LocalDateTime.now();
		 Date date = convertToDateViaInstant(nowDate);
		 stuclass.setCreateDate(date);
		 stuclass.setStudent(u);
		 repo.save(stuclass);
	}
	



}
