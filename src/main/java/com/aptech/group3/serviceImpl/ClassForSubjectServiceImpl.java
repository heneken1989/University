package com.aptech.group3.serviceImpl;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectBasicDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.ClassSubjectEditOneDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.RoomRepository;
import com.aptech.group3.Repository.SemesterRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.service.ClassForSubjectService;

import shared.BaseMethod;

@Service
public class ClassForSubjectServiceImpl implements ClassForSubjectService {
	@Autowired
	private ClassForSubjectRepository classRepository;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private SubjectRepository subjectRepo;
	@Autowired
	private RoomRepository roomRepo;

	@Autowired
	private SemesterRepository semesterRepo;

	@Autowired
	private ModelMapper mapper;
	
	//thanh thêm vào
	   public List<ClassForSubject> findBySemesterIdAndFieldId(Long semesterId,Long fieldId) {
	        return classRepository.findBySemeterIdAnd(semesterId, fieldId);
	    }
	   
	   public List<ClassForSubject> listClassBySemesterId(Long semesterId) {
	        return classRepository.lissClassBySemesterId(semesterId);
	    }
	   
	   
	   public List<ClassForSubject> findAll() {
	        return classRepository.findAll();
	    }
	   
	   //danh sách môn học bằng classid
	    public List<ClassForSubject> getClassSubjects(Long classId) {
	        List<ClassForSubject> classForSubjects = classRepository.findBySubject_Id(classId);
	        return classForSubjects;
	    }
	    
	    		
	
	public List<ClassForSubject> getAllByfieldAndSemester( Long semesterId, Long fieldId){
		return classRepository.findBySemeter_IdAndSubject_field_Id( semesterId, fieldId);
	}
	
	public boolean checkType(Long id){
		boolean result=false;
		ClassForSubject data=classRepository.findById(id).orElse(null);
		List<ClassForSubject> check= classRepository.findByName(data.getName());
		
		if (check.size()>1 ) {
			result=true;
		}
		return result;
	}
	
	public ClassSubjectEditOneDto getEditDto(Long id) {
		
		ClassSubjectEditOneDto data = classRepository.findById(id)
			    .map(e -> {
			    	ClassSubjectEditOneDto dto=	mapper.map(e, ClassSubjectEditOneDto.class);
			    	dto.setRoom_id(e.getRoom().getId());
			    	dto.setSemeter_id(e.getSemeter().getId());
			    	dto.setTeacher_id(e.getTeacher().getId());
			    	dto.setSubject_id(e.getSubject().getId());
			    	return dto;
			    	}).orElse(null);
		
		return data;
	}
	
	public void Edit(ClassSubjectEditOneDto dto) {
		ClassForSubject data =mapper.map(dto, ClassForSubject.class);
		semesterRepo.findById(dto.getSemeter_id()).ifPresent(data::setSemeter);
		subjectRepo.findById(dto.getSubject_id()).ifPresent(data::setSubject);
		userRepo.findById(dto.getTeacher_id()).ifPresent(data::setTeacher);
		roomRepo.findById(dto.getRoom_id()).ifPresent(data::setRoom);
		
		classRepository.save(data);
		
	}


     // HIEN
	public List<ClassForSubject> findByTeacherId(Long teacherID)
	{
       return classRepository.findByTeacherId(teacherID);
	}

	public Page<ClassForSubject> getSubjectByFieldAndSemester(int fieldId, int semesterId,Integer subjectId,Pageable pageable){
		Page<ClassForSubject> data =classRepository.findByFieldIdAndSubjectId(semesterId,fieldId,  subjectId,  pageable);
				
		return data;
	}
	
	
	public ClassForSubject findById(Long id) {
		return classRepository.findById(id).orElse(null);
	}
	
	public List<ClassForSubjectDto> findBySubjectId(Long id) {
		List<ClassForSubject> classss = classRepository.findBySubjectId(id);
		return mapper.map(classss,  new TypeToken<List<ClassForSubjectDto>>() {}.getType());
	}
	
	public List<ClassForSubjectDto> findBySubjectIdAndDate(Long id,Date date) {
		List<ClassForSubject> classss = classRepository.findBySubjectIdAndDateBetweenRegistration(id, date);
		return mapper.map(classss,  new TypeToken<List<ClassForSubjectDto>>() {}.getType());
	}
	
	
	
	
	public ClassForSubject findByClassId(int id) {
		return classRepository.findById(id);
	}
	
	public void create(ClassSubjectCreateDto data) {
		try {
			ClassForSubject sclass = mapper.map(data, ClassForSubject.class);
			userRepo.findById(data.getTeacher_id()).ifPresent(sclass::setTeacher);
			subjectRepo.findById(data.getSubject_id()).ifPresent(sclass::setSubject);
			roomRepo.findById(data.getRoom_id()).ifPresent(sclass::setRoom);
			semesterRepo.findById(data.getRoom_id()).ifPresent(sclass::setSemeter);

			sclass.setDateStart(BaseMethod.convertDate(data.getDate_start()));
			sclass.setDateEnd(BaseMethod.convertDate(data.getDate_end()));

			classRepository.save(sclass);
		} catch (Exception ex) {
			throw ex;
		}

	}

	public void createAll(ClassSubjectAllDto data) {

		ClassForSubject theoClass = mapper.map(data.getTheory(), ClassForSubject.class);
		ClassForSubject ActionClass = mapper.map(data.getTheory(), ClassForSubject.class);

		theoClass = mapForAll(theoClass, true, data);

		classRepository.save(theoClass);

		ActionClass = theoClass = mapForAll(ActionClass, false, data);
		classRepository.save(theoClass);
	}

	
	private ClassForSubject mapForAll(ClassForSubject dataClass, boolean isTheory, ClassSubjectAllDto data) {

		if (isTheory) {
			dataClass.setDateStart(BaseMethod.convertDate(data.getTheory().getDate_start()));
			dataClass.setDateEnd(BaseMethod.convertDate(data.getTheory().getDate_end()));
			userRepo.findById(data.getTheory().getTeacher_id()).ifPresent(dataClass::setTeacher);
			roomRepo.findById(data.getTheory().getRoom_id()).ifPresent(dataClass::setRoom);
		} else {
			dataClass.setDateStart(BaseMethod.convertDate(data.getAction().getDate_start()));
			dataClass.setDateEnd(BaseMethod.convertDate(data.getAction().getDate_end()));
			userRepo.findById(data.getAction().getTeacher_id()).ifPresent(dataClass::setTeacher);
			roomRepo.findById(data.getAction().getRoom_id()).ifPresent(dataClass::setRoom);
		}
		dataClass.setQuantity(data.getQuantity());
		dataClass.setName(data.getName());
		subjectRepo.findById(data.getSubject_id()).ifPresent(dataClass::setSubject);
		semesterRepo.findById(data.getSemeter_id()).ifPresent(dataClass::setSemeter);
		dataClass.setStatus(data.getStatus());

		return dataClass;
	}


	

}
