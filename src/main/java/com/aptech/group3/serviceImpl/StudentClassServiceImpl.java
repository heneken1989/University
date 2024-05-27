package com.aptech.group3.serviceImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CurrentTimestamp;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.AttendanceDto;
import com.aptech.group3.Dto.ClassForSubjectDto;
import com.aptech.group3.Dto.TimeTableApiDto;
import com.aptech.group3.Dto.TimeTableShowDto;
import com.aptech.group3.Repository.AttendanceRepository;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.Attendance;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.StudentClassApiDto;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.StudentClassService;

import shared.BaseMethod;

@Service
public class StudentClassServiceImpl implements StudentClassService {

	@Autowired
	private ClassForSubjectRepository classForSubjectRepository;

	@Autowired
	private StudentClassRepository repo;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AttendanceRepository attendanceRepo;

	public List<ClassForSubject> getListClassStudent(Long studentId, Long SemesterId) {
		return repo.getListClassSubject(studentId, SemesterId);
	}

	public List<TimeTableApiDto> getTimtableApiDto(Long studentId, Date day) {
		int weekday = BaseMethod.getWeekDay(day);
		List<TimeTableApiDto> data = repo.getScheduleByDay(studentId, day, weekday).stream().map(e -> {
			TimeTableApiDto dto = new TimeTableApiDto();
			dto.setClassName(e.getClassforSubject().getName());
			dto.setStartSlot(e.getClassforSubject().getSlotStart());
			dto.setEndSlot(e.getClassforSubject().getSlotEnd());
			dto.setRoom(e.getClassforSubject().getRoom().getName());
			dto.setWeekDay(e.getClassforSubject().getWeekDay());
			dto.setSubjectName(e.getClassforSubject().getSubject().getName());
			Attendance check = attendanceRepo.getDetailByDay(studentId, day, e.getClassforSubject().getId());
			dto.setStatus(check == null ? "waiting" : check.getStatus());

			return dto;
		}).toList();

		return data;

	}

	// new

	public List<StudentClassApiDto> getCurrentClassList(Long studentId, Long SemesterId) {
		List<StudentClassApiDto> data = repo.getCurrentLIstClass(studentId, SemesterId).stream().map(e -> {
			StudentClassApiDto dto = new StudentClassApiDto();
			dto.setId(e.getClassforSubject().getId());
			dto.setClassName(e.getClassforSubject().getName());
			dto.setSubjectName(e.getClassforSubject().getSubject().getName());
			dto.setTeacherName(e.getClassforSubject().getTeacher().getName());

			return dto;
		}).toList();

		return data;
	}

	public List<AttendanceDto> getListStudentByCode(String code, Long classId) {

		List<AttendanceDto> data = repo.getStudentByCodeAndClassId(code, classId).stream().map(e -> {
			AttendanceDto dto = new AttendanceDto();
			dto.setStudent_code(e.getStudent().getCode());
			dto.setStudent_id(e.getStudent().getId());
			dto.setStudent_name(e.getStudent().getName());

			return dto;
		}).toList();

		return data;
	}

	public List<AttendanceDto> getListStudentInClass(Long classId) {

		List<AttendanceDto> data = repo.findByClassforSubject_Id(classId).stream().map(e -> {
			AttendanceDto dto = new AttendanceDto();
			dto.setStudent_code(e.getStudent().getCode());
			dto.setStudent_id(e.getStudent().getId());
			dto.setStudent_name(e.getStudent().getName());

			return dto;
		}).toList();

		return data;

	}

	public List<TimeTableShowDto> getCurrentTimeTable(Long studentId, Date dateStart, Date dateEnd, Long semesterId) {

		List<TimeTableShowDto> data = repo.getcalendar(studentId, dateStart, dateEnd, semesterId).stream().map(e -> {
			TimeTableShowDto dto = new TimeTableShowDto();
			dto.setEndSlot(e.getClassforSubject().getSlotEnd());
			dto.setStartSlot(e.getClassforSubject().getSlotStart());
			dto.setName(e.getClassforSubject().getSubject().getName());
			dto.setRoom(e.getClassforSubject().getRoom().getName());
			dto.setWeekDay(e.getClassforSubject().getWeekDay());
			dto.setClass_id(e.getClassforSubject().getId());
			return dto;
		}).toList();
		return data;
	}

	public boolean CheckStuentInClass(Long stuentId, Long ClassId) {
		StudentClass check = repo.findByStudent_IdAndClassforSubject_Id(stuentId, ClassId);
		if (check == null) {
			return false;
		} else {
			return true;
		}
	}

	public static Date convertToDateViaInstant(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public List<StudentClass> findSubjectByStudentId(Long studentId) {
		return repo.findByStudentId(studentId);
	}

	public List<StudentClass> findEarliestByStatus(String status) {
		return repo.findEarliestByStatus(status);
	}

	public void RegisterClass(ClassForSubjectDto dto, Long userId) {
		ClassForSubject classForSubject = classForSubjectRepository.getById(dto.getId());
		StudentClass stuclass = new StudentClass();
		stuclass.setClassforSubject(classForSubject);
		User u = userRepository.getById(userId);

		// inscrea current quantity of class +1
		int quantity = classForSubject.getQuantity();
		int maxquantity = classForSubject.getMaxQuantity();
		if (quantity >= maxquantity) {
			stuclass.setStatus("WaitingList");
		} else {
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

	public void RegisterClassMobile(Long classId, Long userId) {
		ClassForSubject classForSubject = classForSubjectRepository.getById(classId);
		StudentClass stuclass = new StudentClass();
		stuclass.setClassforSubject(classForSubject);
		User u = userRepository.getById(userId);

		// inscrea current quantity of class +1
		int quantity = classForSubject.getQuantity();
		int maxquantity = classForSubject.getMaxQuantity();
		if (quantity >= maxquantity) {
			stuclass.setStatus("WaitingList");
		} else {
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

	public List<StudentClass> findByClassForSubjectId(Long classId) {
		return repo.findByClassforSubjectId(classId);
	}

	public void updateItemsStatusToPayment(List<Long> idList) {
		// Fetch the items from the database based on the IDs in the idList
		List<StudentClass> items = repo.findAllById(idList);

		for (StudentClass item : items) {
			item.setStatus("payment");
		}

		repo.saveAll(items);
	}

	public List<StudentClass> findByStudentIdAndStatus(Long studentId, String status) {
		return repo.findByStudentIdAndStatus(studentId, status);
	}

	// thanh
	public List<StudentClass> getListStudentByClassId(Long classId) {
		return repo.getStudentByClassId(classId);
	}

	public List<User> getStudentsByClassAndSubject(Long classId, Long subjectId) {
		// Lấy danh sách các học sinh đang học môn học có subjectId trong lớp học có
		// classId từ repository
		List<StudentClass> studentClasses = repo.findByClassforSubject_IdAndClassforSubject_Subject_Id(classId,
				subjectId);

		// Tạo danh sách học sinh từ danh sách StudentClass
		List<User> students = new ArrayList<>();
		for (StudentClass studentClass : studentClasses) {
			students.add(studentClass.getStudent());
		}

		return students;
	}

}
