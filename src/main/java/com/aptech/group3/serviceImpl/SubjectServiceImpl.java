package com.aptech.group3.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Repository.SubjectLevelRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepo;
	@Autowired
	private SubjectLevelRepository LevelRepo;

	@Autowired
	private ModelMapper mapper;

	public List<Subject> searchSubject(String name, Integer fieldId, Integer levelId) {
		List<Subject> data = subjectRepo.findByMultipleCriteria(name, levelId, fieldId);
		return data;
	}

	public List<Subject> getByField(Long id) {
		return subjectRepo.findByFieldId(id);
	}

	public List<Subject> findAll() {
		return subjectRepo.findAll();
	}

	public int getCredit(int id) {
		return subjectRepo.getCreditById(id);
	}

	public List<SubjectLevel> listSubjectLevel() {
		return LevelRepo.findAll();
	}

	public List<Subject> findBySubjectName(String name) {
		return subjectRepo.findByNameContainingIgnoreCase(name);
	}

	public Optional<Subject> findbyId(Long id) {
		return subjectRepo.findById(id);
	}

	public List<Subject> findByLevel(Long levelId) {
		return subjectRepo.findBySubjectlevelId(levelId);
	}

	public Subject saveSubject(Subject sub) {

		// Retrieve the Category object based on the provided category_id
		// Long categoryId = room.getCategory().getId();
		// Category category = cateRepo.findById(categoryId)
		// .orElseThrow(() -> new RuntimeException("Category not found with id: " +
		// categoryId));

		// Set the retrieved Category object to the room's category field
		// room.setCategory(category);

		return subjectRepo.save(sub);
	}

	public List<Subject> listSubject() {
		return subjectRepo.findAll();
	}

	public void updateSubject(Subject sub) {
		Long RoomId = sub.getId();
		if (subjectRepo.existsById(RoomId)) {
			subjectRepo.save(sub);
		}

	}

	public List<SubjectDto> findByStudent(User student, Long field) {
		List<Subject> listS = subjectRepo.findSubjectsForStudent(student, field);
		return mapper.map(listS, new TypeToken<List<SubjectDto>>() {
		}.getType());
	}

}
