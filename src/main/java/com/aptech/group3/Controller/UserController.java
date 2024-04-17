package com.aptech.group3.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.EmailService;
import com.aptech.group3.service.FiledService;
import com.aptech.group3.service.SemesterService;
import com.aptech.group3.service.SubjectService;
import com.aptech.group3.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;

@Controller
//@PreAuthorize("hasAuthority('EMPLOYEE')")
@RequestMapping({ "/user" })
public class UserController {
//https://bootsnipp.com/snippets/K0ZmK
	@Autowired
	private EmailService emailService;
	@Autowired
	private ClassForSubjectService classService;

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
	/*
	 * chọn học kỳ -> list ra các class-subject có học kỳ bằng nè ->lấy
	 * student-class kiểm tra class_id trong studentclass có bằng với id class không
	 * và hiển thị tất cả student có class_id bằng
	 */
//
//	@GetMapping("/allClass")
//	public ResponseEntity<List<Semeter>> findAll(@RequestParam("semesterId") String semesterId) {
//		System.out.println(semesterId);
//		List<Semeter> semesterList = semesterService.findAll();
//		if (semesterList.isEmpty()) {
//			return ResponseEntity.noContent().build();
//		}
//		return ResponseEntity.ok(semesterList);
//	}

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
		// Lấy thông tin người dùng
		User user = userService.findById(id);

		// Lấy danh sách các trường của người dùng
		List<Field> userFields = userService.findFieldsByUserId(id);

		// Lấy tất cả các trường
		List<Field> allFields = filedService.getAllFields();

		model.addAttribute("user", user);
		model.addAttribute("userFields", userFields);
		model.addAttribute("allFields", allFields);
		return "admin_user/update";
	}

	@PostMapping("/update/{id}")
	public String saveUpdate(@Valid @ModelAttribute("user") UserDto dto, BindingResult result,
			@RequestParam(name = "id") Long id) {
		if (result.hasErrors()) {
			return "admin_user/update";
		}
		userService.update(dto);
		return "redirect:/user/list";
	}

	@GetMapping("/list")
	public String showByRole(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "type", defaultValue = "STUDENT") String role) {
		Pageable paging = PageRequest.of(page - 1, 10);
		model.addAttribute("data", userService.findByRole(role, paging));
		model.addAttribute("type", role);

		model.addAttribute("semesterAll", semesterService.findAll());
		model.addAttribute("currentSemester", semesterService.getCurrentSemester());
	
		return "admin_user/index";
	}

	@GetMapping("/create")
	public String createUser(Model model) {
		UserDto data = new UserDto();
		List<Field> fieldsList = filedService.getAllFields();
		model.addAttribute("fields", fieldsList);
		model.addAttribute("data", data);
		return "admin_user/create";
	}

	@PostMapping("/create")
	public String saveUser(Model model, @Valid @ModelAttribute("data") UserDto data, BindingResult result,
			HttpServletRequest request) {
		String[] listField = request.getParameterValues("field[]");
		// kiểm_tra_nhập_dữ_liệu_có_sai_không
//		if (result.hasErrors()) {
//			model.addAttribute("data", data);
//			return "admin_user/create";
//		}

		// kiểm_tra_role_tạo_tài_khoản
		
		if (data.getRole().equals("STUDENT")) {
			int studentCount = userService.countStudents();
			String code = "ST24" + String.format("%04d", studentCount + 1);
			data.setCode(code);

		} else if (data.getRole().equals("TEACHER")) {
			int teachersCount = userService.countTeachers();
			String code = "TC24" + String.format("%04d", teachersCount + 1);
			data.setCode(code);
			List<Long> listLong = new ArrayList<>();
			for (String a : listField) {
				listLong.add((long) Integer.parseInt(a));
			}
			data.setFields(listLong);

		} else if (data.getRole().equals("EMPLOYEE")) {
			int employeeCount = userService.countEmployees();
			String code = "EM24" + String.format("%04d", employeeCount + 1);
			data.setCode(code);
			List<Long> listLong = new ArrayList<>();
			for (String a : listField) {
				listLong.add((long) Integer.parseInt(a));
			}
			data.setFields(listLong);
		}
		
	    boolean emailExists = userService.checkIfEmailExists(data.getEmail());
	    if (emailExists) {
	        model.addAttribute("error", "Email already exists");
	        return "admin_user/create";
	    }

		// tạo_mật_khẩu_gửi_tới_email
		String randomPassword = UUID.randomUUID().toString().substring(0, 6);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(randomPassword);
		data.setPassword(encodedPassword);

		// submit_create_account
		userService.create(data);
		emailService.sendPasswordEmail(data.getEmail(), randomPassword);
		return "redirect:/user/list";
	}

	@GetMapping("/export")
	public void exportToExcel(@RequestParam(name = "role", required = false) String role, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");
		// tên, code, stt, ký tên, ghi chú, điểm
		// export form search theo mã lớp, theo môn học (show kỳ hiện tại)
		List<User> users;
		if (role != null && !role.isEmpty()) {
			users = userService.getUsersByRole(role);
		} else {
			users = userService.listAll();
		}

		userService.exportToExcel(users, response.getOutputStream());

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

//	
//	 @PostMapping("/upload")
//	    public String uploadExcelFile(@RequestParam("file") MultipartFile file) {
//		 
//	        try {
//	            List<User> users = userService.readUsersFromExcel(file);
//	            userService.saveUsers(users);
//	            return "redirect:/user/list";
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	            return "Failed to upload file. Please try again.";
//	        }
//	    }

	/*
	 * @PostMapping("/create") public String saveUser(Model
	 * model, @ModelAttribute("data") UserDto data) { UUID random =
	 * UUID.randomUUID(); String newpass =
	 * bcrypt.encode(random.toString().substring(0, 6)); userService.create(data,
	 * newpass); return "admin_user/index"; }
	 */

}
