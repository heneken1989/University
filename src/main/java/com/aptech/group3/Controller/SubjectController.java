package com.aptech.group3.Controller;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.aptech.group3.Dto.ClassSubjectAllDto;
import com.aptech.group3.Dto.ClassSubjectCreateDto;
import com.aptech.group3.Dto.ClassType;
import com.aptech.group3.Dto.RequiredSubjectDto;
import com.aptech.group3.Dto.SubjectCreateDto;
import com.aptech.group3.Dto.SubjectDto;
import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.Repository.FiledRepository;
import com.aptech.group3.Repository.SubjectRepository;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.RequiredSubject;
import com.aptech.group3.entity.SubjectLevel;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.RequiredSubjectService;
import com.aptech.group3.service.SubjectLevelService;
import com.aptech.group3.service.SubjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping({ "/admin/subject" })
public class SubjectController {

	@Autowired
	private SubjectService subService;

	@Autowired
	private RequiredSubjectService reqService;
	
	@Autowired
	private SubjectLevelService sublvService;
	
	
	@Autowired
	private SubjectRepository subjectRepository;

	@GetMapping("/list")
	public String Subject(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,

			@RequestParam(name = "level", required = false) Long se,

			@RequestParam(name = "page", required = false, defaultValue = "1") int page) {
		Pageable paging = PageRequest.of(page - 1, 10);
		Long fieldId = currentUser.getUser().getFields().get(0).getId();
		
		
		
		Page<Subject> data = subService.getListPage(fieldId, se == null || se==0 ? null : se, paging);
		List <SubjectLevel> sblevel = sublvService.findAll();
		model.addAttribute("data", data);
		model.addAttribute("sblevels",sblevel);
		model.addAttribute("selectLevel",se == null ?0 : se);
		return "subject/index";
	}

	@GetMapping("/create")
	public String showcreatesubject(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		SubjectCreateDto data = new SubjectCreateDto();
		model.addAttribute("data", data);

		List<SubjectLevel> subjectLevels = subService.listSubjectLevel();
		// List<Field>fields = subService.findall();
		// model.addAttribute("subjects",
		// subService.getByField(currentUser.getUser().getFields().get(0).getId()));
		model.addAttribute("requiredSubjects", new HashSet<RequiredSubject>());

		model.addAttribute("field", currentUser.getUser().getFields().get(0).getId());
		// model.addAttribute("fields",fields);
		model.addAttribute("subjectlevels", subjectLevels);
		model.addAttribute("type", ClassType.values());

		return "subject/create";
	}

	@PostMapping("/create")
	public String createsubject(Model model, @ModelAttribute("data") @Valid SubjectCreateDto data,
			BindingResult bindingResult, HttpServletRequest request) {

		String[] listField = new String[] {};
		String[] listField1 = new String[] {};
	
		listField = request.getParameterValues("field[]");
		listField1 = request.getParameterValues("field1[]");
		Subject newsub = subService.create(data);
		if(listField.length !=0) {
			
			for (String a : listField)
			{
				System.out.print("list filed :"+listField.length);		
				RequiredSubjectDto dto = new RequiredSubjectDto();
				dto.setRequiredsubjectId((long) Integer.parseInt(a));
				
				dto.setSubjectId(newsub.getId());
				dto.setStatus("PASS");
				
				reqService.createreq(dto);	
			}
		}
		if(listField1.length !=0) {
			
			
			
			for (String a : listField1)
			{
				System.out.print("list filed :"+listField.length);		
				RequiredSubjectDto dto = new RequiredSubjectDto();
				dto.setRequiredsubjectId((long) Integer.parseInt(a));
				dto.setSubjectId(newsub.getId());
				dto.setStatus("OPTIONAL");
				reqService.createreq(dto);	
			}
		}
		
		
		return "redirect:/admin/subject/list";
	}

	@GetMapping("/updatesubject/{id}")
	public String showupdatesubject(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		// lay thong tin mon hoc
		Subject sb = subjectRepository.getById(id);
		
		// lay tat ca truong cua subject

		List<SubjectLevel> subjectLevels = subService.listSubjectLevel();
		
		
		
		model.addAttribute("type", ClassType.values());
		model.addAttribute("subject", sb);
		model.addAttribute("fields",currentUser.getUser().getFields().get(0).getId());
		model.addAttribute("subjectLevels", subjectLevels);

		return "subject/update";
	}

	/* @RequestParam(name = "id") */
	@PostMapping("/update/{id}")
	public String saveupdate(Model model, @ModelAttribute("subject") @Valid SubjectCreateDto dto, BindingResult result,
			@RequestParam(name = "id")	 Long id) {
		
		subService.updatesubject(dto);
		return "redirect:/admin/subject/list";
	}
	
	
	
	@GetMapping("/hidesubject/{id}")
    public String hideSubject(@PathVariable("id") Long id, Model model) {
        subService.hideById(id);
		/* return "redirect:/subjects"; */
        return "redirect:/admin/subject/list";
    }
	
	
	@GetMapping("/hiddenSubject")
	public String showSubject(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,

			@RequestParam(name = "level", required = false) Long se,

			@RequestParam(name = "page", required = false, defaultValue = "1") int page) {
		Pageable paging = PageRequest.of(page - 1, 10);
		Long fieldId = currentUser.getUser().getFields().get(0).getId();
		
		Page<Subject> data = subService.getListPage(fieldId, se == null || se==0 ? null : se, paging);
		List <SubjectLevel> sblevel = sublvService.findAll();
		model.addAttribute("data", data);
		model.addAttribute("sblevels",sblevel);
		model.addAttribute("hiddenselectLevel",se == null ?0 : se);
		return "subject/hiddensubject";
	}

	
	@GetMapping("/unhidesubject/{id}")
    public String ShowSubject(@PathVariable("id") Long id, Model model) {
        subService.showById(id);
		/* return "redirect:/subjects"; */
        return "redirect:/admin/subject/list";
    }
	
	
	/*
	 * @GetMapping("/detailsubject/{id}") public String
	 * detailsubject(@PathVariable("id") Long id, Model
	 * model, @AuthenticationPrincipal CustomUserDetails currentUser) {
	 * 
	 * }+
	 */
	 
	

	@GetMapping("/listrequiredsubject")
	public String listreqsub(Model model, @AuthenticationPrincipal CustomUserDetails currentUser) {
		List<RequiredSubject> req = reqService.findAll();
		model.addAttribute("req", req);

		return "subject/reqsubject";
	}

	@GetMapping("/updatereq/{id}")
	public String showupdatereq(@PathVariable("id") Long id, Model model) {
		// lay thong tin reqsub
		RequiredSubject re = reqService.findById(id);
		// lay thong tin can update
		model.addAttribute("re", re);
		return "subject/updatereq";
	}

	@PostMapping("/updatereq/{id}")
	public String saveUpdatereq(Model model, @ModelAttribute("requiredsubject") @Valid RequiredSubjectDto dto,
			BindingResult result, @RequestParam(name = "id") Long id) {
		if (result.hasErrors()) {
			return "subject/updatereq";
		}
		reqService.update(dto);
		return "redirect:/admin/subject/listrequiredsubject";
	}

}
