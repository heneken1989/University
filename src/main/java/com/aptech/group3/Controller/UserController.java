package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;

import com.aptech.group3.Dto.UpdateProfileDto;
import com.aptech.group3.Dto.UserCreateDto;
import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.Semeter;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.EmailService;
import com.aptech.group3.service.FiledService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;

@Controller
@PreAuthorize("hasAuthority('EMPLOYEE')")
@RequestMapping({ "/user" })
public class UserController {
//https://bootsnipp.com/snippets/K0ZmK
	@Autowired
	private EmailService emailService;
	@Autowired
	private ClassForSubjectService classService;

	@Autowired
	private StudentClassService studentService;

	@Autowired
	private FiledService filedService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bcrypt;

	@Autowired
	private SemesterService semesterService;

	public String saveUserProfile(Model model, @Valid @ModelAttribute("profileUser") UserCreateDto data,
			BindingResult result, @RequestParam(value = "avatar", required = false) MultipartFile avatarFile,
			HttpServletRequest request) throws IOException {
		System.out.println("DATA:: " + data);

		boolean emailExists = userService.checkIfEmailExists(data.getEmail());
		if (emailExists) {
			model.addAttribute("error", "Email already exists");
			return "admin_user/create";
		}
		// lưu avatar vào uploads
		Path path = Paths.get("uploads/");
		try {
			InputStream inputStream = avatarFile.getInputStream();
			Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			data.setAvatar(avatarFile.getOriginalFilename().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("notification", "Create success!");
		return null;
	}

	@GetMapping("/detail/{id}")
	public String detail(Model model, @PathVariable(name = "id") long id) {
		List<String> fieldNames = userService.getFieldNamesByUserId(id);
		model.addAttribute("user", userService.findById(id));
		model.addAttribute("fields", fieldNames);
		System.out.println("fIELDD::" + fieldNames);
		return "admin_user/detail";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		User user = userService.findById(id);
		List<Field> userFields = userService.findFieldsByUserId(id);
		List<Field> allFields = filedService.getAllFields();
		model.addAttribute("user", user);
		model.addAttribute("userFields", userFields);
		model.addAttribute("allFields", allFields);
		return "admin_user/update";
	}

	@PostMapping("/update/{id}")
	public String saveUpdate(@Valid @ModelAttribute("user") UserCreateDto dto, BindingResult result,
			@RequestParam(name = "avatar", required = false) MultipartFile avatarFile,
			@RequestParam(name = "id") Long id, Model model, RedirectAttributes redirectAttributes) {

		if (avatarFile != null && !avatarFile.isEmpty()) {
			try {
				Path path = Paths.get("uploads/"); // Lưu avatar mới vào thư mục uploads
				InputStream inputStream = avatarFile.getInputStream();
				Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				dto.setAvatar(avatarFile.getOriginalFilename().toLowerCase());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			User currentUser = userService.findById(id);
			dto.setAvatar(currentUser.getAvatar());
		}
		userService.update(dto);
		return "redirect:/user/list";
	}

	@GetMapping("/list")
	public String showByRole(@AuthenticationPrincipal CustomUserDetails currentUser, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "type", defaultValue = "ALL") String type,
			@RequestParam(name = "notification", required = false) String notification) {
		Pageable paging = PageRequest.of(page - 1, 10);

		model.addAttribute("data", userService.findByRole(type, paging));
		model.addAttribute("type", type);
		model.addAttribute("selectedType", type);
		model.addAttribute("notification", notification);
		return "admin_user/index";
	}

	@GetMapping("/create")
	public String createUser(Model model) {

		UserCreateDto data = new UserCreateDto();
		List<Field> fieldsList = filedService.getAllFields();
		model.addAttribute("fields", fieldsList);
		model.addAttribute("data", data);

		return "admin_user/create";
	}

	@PostMapping("/create")
	public String saveUser(Model model, @RequestParam(name = "image", required = false) MultipartFile avatarFile,
			@ModelAttribute("data") @Valid UserCreateDto data, BindingResult result, HttpServletRequest request)
			throws IOException {
		String[] listField = request.getParameterValues("field[]");
		if (result.hasErrors()) {

			List<Field> fieldsList = filedService.getAllFields();
			model.addAttribute("data", data);
			for (ObjectError error : result.getAllErrors()) { // 1.
				String fieldErrors = ((FieldError) error).getField(); // 2.
				System.out.println(fieldErrors);
			}
			model.addAttribute("fields", fieldsList);
			return "admin_user/create";
		}
		if (data.getRole().equals("STUDENT")) {
			int studentCount = userService.countStudents();
			String code = "ST24" + String.format("%04d", studentCount + 1);
			data.setCode(code);
		} else if (data.getRole().equals("TEACHER")) {
			int teachersCount = userService.countTeachers();
			String code = "TC24" + String.format("%04d", teachersCount + 1);
			data.setCode(code);
			List<Long> listLong = new ArrayList<>();
			if (listField != null) {
				for (String a : listField) {
					listLong.add((long) Integer.parseInt(a));
				}
				data.setFields(listLong);
			} else {
				data.setFields(null);
			}

		} else if (data.getRole().equals("EMPLOYEE")) {
			int employeeCount = userService.countEmployees();
			String code = "EM24" + String.format("%04d", employeeCount + 1);
			data.setCode(code);
			List<Long> listLong = new ArrayList<>();
			if (listField != null) {
				for (String a : listField) {
					listLong.add((long) Integer.parseInt(a));
				}
				data.setFields(listLong);
			} else {
				data.setFields(null);
			}
		}
		boolean emailExists = userService.checkIfEmailExists(data.getEmail());
		if (emailExists) {
			model.addAttribute("error", "Email already exists");
			return "admin_user/create";
		}
		// lưu avatar vào uploads
		Path path = Paths.get("uploads/");
		try {
			InputStream inputStream = avatarFile.getInputStream();
			Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			data.setAvatar(avatarFile.getOriginalFilename().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// tạo_mật_khẩu_gửi_tới_email
		String randomPassword = UUID.randomUUID().toString().substring(0, 6);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(randomPassword);
		data.setPassword(encodedPassword);
		userService.create(data);
		emailService.sendPasswordEmail(data.getEmail(), randomPassword);
		model.addAttribute("notification", "Create success!");
		return "redirect:/user/list?notification=Create%20success!";

	}

	@GetMapping("/export")
	public void exportToExcel(HttpServletResponse response, HttpSession session, Model model) throws IOException {
		Long classId = (Long) session.getAttribute("classId");
		Long subjectId = (Long) session.getAttribute("subjectId");
		List<User> students = studentService.getStudentsByClassAndSubject(classId, subjectId);

		// Tiếp tục xử lý xuất Excel với danh sách sinh viên đã lấy được
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
		userService.exportToExcel(students, response.getOutputStream());
	}

	@PostMapping("/upload")
	public String uploadExcelFile(@RequestParam("file") MultipartFile file) {
		try {
			List<User> users = userService.readUsersFromExcel(file);
			for (User user : users) { // send password to email
				emailService.sendPasswordEmail(user.getEmail(), user.getPassword());
			}
			userService.saveAndEncoderPassword(users);

			return "redirect:/user/list";
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed to upload file. Please try again.";
		}
	}

	@GetMapping("/profile")
	public String showUpdateProfileForm(@AuthenticationPrincipal CustomUserDetails currentUser, Model model) {
		User user = userService.findByEmail(currentUser.getUsername());
		model.addAttribute("user", user);
		return "admin_user/edit_profile";
	}

	@PostMapping("/profile")
	public String saveUpdateProfileProfile(@Valid @ModelAttribute("user") UpdateProfileDto dto, BindingResult result,
			@RequestParam(name = "avatar", required = false) MultipartFile avatarFile,
			RedirectAttributes redirectAttributes) {
		String randomPassword = UUID.randomUUID().toString().substring(0, 6);
		if (avatarFile != null && !avatarFile.isEmpty()) {
			try {
				Path path = Paths.get("uploads/");
				InputStream inputStream = avatarFile.getInputStream();
				Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				dto.setAvatar(avatarFile.getOriginalFilename().toLowerCase());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			User currentUser = userService.findById(dto.getId());
			dto.setAvatar(currentUser.getAvatar());
			
			if(dto.getEmail()!=currentUser.getEmail()) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				String encodedPassword = encoder.encode(randomPassword);
				dto.setPassword(encodedPassword);
			}
		}
		
		
		userService.updateProfile(dto);
		emailService.sendPasswordEmail(dto.getEmail(), randomPassword);
		redirectAttributes.addAttribute("newAvatar", dto.getAvatar());
		redirectAttributes.addAttribute("newName", dto.getName());
	    redirectAttributes.addAttribute("newEmail", dto.getEmail());
	    redirectAttributes.addAttribute("newPhone", dto.getPhone());
	    redirectAttributes.addAttribute("newAddress", dto.getAddress());
	    redirectAttributes.addAttribute("newInfo", dto.getInfomation());
		return "redirect:/index";
	}
}
