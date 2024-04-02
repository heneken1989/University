package com.aptech.group3.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aptech.group3.Dto.DiscussRoomDto;
import com.aptech.group3.entity.DiscussRoom;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.TeacherRegisted;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.DiscussMessageService;
import com.aptech.group3.service.DiscussRoomService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.TeacherRegistedService;

import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/discuss" })
public class DiscussController {

	@Autowired
	SemesterService semesterService;

	@Autowired
	ClassForSubjectService classService;

	@Autowired
	TeacherRegistedService teacherService;
	
	@Autowired
	DiscussMessageService messageService;
	
	@Autowired
	
	StudentClassService stuService;

	@Autowired
	DiscussRoomService service;

	@GetMapping("/list/{id}")
	public String list(Model model, @PathVariable(name = "id") Long id ,@AuthenticationPrincipal CustomUserDetails currentUser) {
	
		model.addAttribute("data", service.getList(id));
		model.addAttribute("role",currentUser.getAuthorities().toString() );
	
		
		return "discuss/list";
	}
	
	@GetMapping("/room/edit/{id}")
	public String showEdit(Model model, @PathVariable(name = "id") Long id ,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
	
		
		
		if(currentUser.getAuthorities().toString()!="TEACHER") {
			return "redirect:/discuss/room";
		}else {
			boolean checkTeacher = service.checkTeacherInRoom(id, currentUser.getUserId());
			if(!checkTeacher) {
				return "redirect:/discuss/room";
			}
		}
		model.addAttribute("data",service.getById(id.intValue()));
		return "discuss/edit";
	}

	@GetMapping("/create")
	public String showCreate(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
	
		Semeter se = semesterService.getCurrentSemester();
		List<TeacherRegisted> check = teacherService.getByTeacherId(currentUser.getUserId(), se.getId());
		model.addAttribute("class", check);
		DiscussRoomDto data = new DiscussRoomDto();
		model.addAttribute("data", data);
		return "discuss/create";
	}

	@PostMapping("/create")
	public String create(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@ModelAttribute("data") @Valid DiscussRoomDto data, BindingResult bindingResult) {
	
		if (bindingResult.hasErrors()) {
			Semeter se = semesterService.getCurrentSemester();
			List<TeacherRegisted> check = teacherService.getByTeacherId(currentUser.getUserId(), se.getId());
			model.addAttribute("class", check);
			model.addAttribute("data", data);
			return "discuss/create";
		}

		data.setTeacher_id(currentUser.getUserId());
		service.create(data);
		return "redirect:/discuss/room";

	}

	@GetMapping("/room")
	public String detail() {
		return "discuss/detail";
	}

	@GetMapping("/room/{id}")
	public String chat(Model model, @PathVariable(name = "id") int id,
			@AuthenticationPrincipal CustomUserDetails currentUser) {
		
		DiscussRoom data = service.getById(id);
		model.addAttribute("student", currentUser.getUserId());
		model.addAttribute("topic",data.getTopic());
		model.addAttribute("room", id);
		model.addAttribute("messages",messageService.getMessageByRoomId((long)id));
		if (currentUser.getAuthorities().toString() == "TEACHER") {
			boolean checkTeacher = service.checkTeacherInRoom((long)id, currentUser.getUserId());
			if (checkTeacher) {
				return "discuss/chat";
			}else {
				return "redirect:/discuss/room";
			}
		} else {
			boolean check = stuService.CheckStuentInClass(currentUser.getUserId(), data.getOwner().getId());
			if (!check) {
				return "redirect:/discuss/room";
			}
		}
		return "discuss/chat";
	}
}
