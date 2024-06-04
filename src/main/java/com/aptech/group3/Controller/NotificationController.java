package com.aptech.group3.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aptech.group3.Dto.NoftifyType;
import com.aptech.group3.Dto.NotifyCreateDto;
import com.aptech.group3.Dto.UserStatus;
import com.aptech.group3.entity.Notification;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.model.NotificationMessage;
import com.aptech.group3.service.FirebaseMessageService;
import com.aptech.group3.service.NotificationService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.UserService;

import jakarta.validation.Valid;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('ADMMIN')")
@RequestMapping({ "/admin/notify" })
public class NotificationController {

	@Autowired
	private NotificationService service;

	@Autowired
	private SemesterService semesterService;

	@Autowired
	private FirebaseMessageService fireBasseService;

	@Autowired
	private StudentClassService studentClassService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public String index(Model model,
			@AuthenticationPrincipal CustomUserDetails currentUser,
			@RequestParam(name = "semester", required = false) Integer se,
			@RequestParam(name = "page", defaultValue = "1") int page) {

		List<Semeter> semester = semesterService.findAll();
		model.addAttribute("semesters", semester);
		Long semesterId = semesterService.getCurrentSemester().getId();
		Long fieldId = currentUser.getUser().getFields().get(0).getId();
		Pageable paging = PageRequest.of(page - 1, 10);
		Page<Notification> data = service.getByFieldAndSemester(fieldId, se == null ? semesterId : se, paging);
		model.addAttribute("currentSemester", se == null ? semesterId : se);
		model.addAttribute("data", data);

		return "page/notification/index";
	}

	@GetMapping("/create")
	public String create(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		NotifyCreateDto data = new NotifyCreateDto();
		data.setField_id(currentUser.getUser().getFields().size() == 0 ? null
				: currentUser.getUser().getFields().get(0).getId());

		model.addAttribute("data", data);

		model.addAttribute("notifyTypes", NoftifyType.values());
		model.addAttribute("field", currentUser.getUser().getFields().size() == 0 ? null
				: currentUser.getUser().getFields().get(0).getName());

		return "page/notification/create";
	}

	@PostMapping("/create")
	public String createAction(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
			@ModelAttribute("data") @Valid NotifyCreateDto data,BindingResult bindingResult) {
		data.setCreated_at(new Date());
		if (data.getType() == NoftifyType.ALL) {
			data.setField_id(null);
		}
		
		if(bindingResult.hasErrors()) {
			
		
			data.setField_id(currentUser.getUser().getFields().size() == 0 ? null
					: currentUser.getUser().getFields().get(0).getId());

			model.addAttribute("data", data);

			model.addAttribute("notifyTypes", NoftifyType.values());
			model.addAttribute("field", currentUser.getUser().getFields().size() == 0 ? null
					: currentUser.getUser().getFields().get(0).getName());

			return "page/notification/create";
		}
		
		if(data.getType()==NoftifyType.CLASS&&  data.getClass_id()== null) {
			data.setField_id(currentUser.getUser().getFields().size() == 0 ? null
					: currentUser.getUser().getFields().get(0).getId());

			model.addAttribute("data", data);
			model.addAttribute("classError",true);

			model.addAttribute("notifyTypes", NoftifyType.values());
			model.addAttribute("field", currentUser.getUser().getFields().size() == 0 ? null
					: currentUser.getUser().getFields().get(0).getName());

			return "page/notification/create";
		}
		
		Semeter current = semesterService.getCurrentSemester();

		service.create(data, currentUser.getUser(), current); 
		
		  Map<String, String> map = new HashMap<String, String>();
		  map.put("key1", "value1");

		if (data.getType() == NoftifyType.CLASS) {

			List<StudentClass> listUser = studentClassService.findByClassForSubjectId(data.getClass_id());
			List<NotificationMessage> listMess = new ArrayList<NotificationMessage>();

			listUser.forEach(e -> {
				if (e.getStudent().getMobileCode() != null) {
					NotificationMessage mess = new NotificationMessage();
					mess.setBody(data.getContent());
					mess.setTitle(data.getContent());
					 mess.setData(map);
					mess.setRecripientToken(e.getStudent().getMobileCode());
					  listMess.add(mess);
				}
			});
			fireBasseService.sentManyNotification(listMess);
		}
		
		if(data.getType()==NoftifyType.FIELD) {
			
			List<User> listUser= userService.getListUSerByField(UserStatus.LEARNING, data.getField_id(), "STUDENT");
		
			List<NotificationMessage> listMess = new ArrayList<NotificationMessage>();
			
			for (User  e : listUser) {
				
				  if (e.getMobileCode() != null) {
				  
				  NotificationMessage mess = new NotificationMessage();
				  mess.setBody(data.getContent()); mess.setTitle(data.getContent());
				  mess.setData(map);
				  mess.setRecripientToken(e.getMobileCode()); 
				  listMess.add(mess);
				  }
			
			}
			
			fireBasseService.sentManyNotification(listMess);
		}
		
		if(data.getType()==NoftifyType.ALL) {
			List<User> listUser= userService.getListUserByRoleAnddStatus(UserStatus.LEARNING, "STUDENT");
			
			List<NotificationMessage> listMess = new ArrayList<NotificationMessage>();
			
			listUser.forEach(e -> {
				if (e.getMobileCode() != null) {
					NotificationMessage mess = new NotificationMessage();
					mess.setBody(data.getContent());
					mess.setTitle(data.getContent());
					 mess.setData(map);
					mess.setRecripientToken(e.getMobileCode());
					  listMess.add(mess);
				}
			});
			fireBasseService.sentManyNotification(listMess);
		}
		
	
		return "redirect:/admin/notify/list";
	}

}
