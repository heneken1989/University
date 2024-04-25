package com.aptech.group3.Controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aptech.group3.Dto.UserCreateDto;
import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.entity.Field;
import com.aptech.group3.service.EmailService;
import com.aptech.group3.service.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class LoginSuccessController {
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/images";

	@GetMapping("/success")
	public String loginSuccess() {
		return "index";
	}

	@GetMapping("/register")
	public String create(Model model) {
		UserDto data = new UserDto();
		model.addAttribute("data", data);
		return "register";
	}

	@PostMapping("/register")
	public String saveUser(Model model, 
			@RequestParam("email") String email, @RequestParam("name") String name,
			@RequestParam("phone") String phone, @RequestParam("avatar") MultipartFile avatarFile,
			HttpServletRequest request) {
		boolean emailExists = userService.checkIfEmailExists(email);
		if (emailExists) {
			model.addAttribute("error", "Email already exists");
			return "register";
		}
		UserCreateDto user = new UserCreateDto();
		user.setName(name);
		user.setPhone(phone);
		user.setEmail(email);

		Path path = Paths.get("uploads/");
		try {
			InputStream inputStream = avatarFile.getInputStream();
			Files.copy(inputStream, path.resolve(avatarFile.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			user.setAvatar(avatarFile.getOriginalFilename().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// tạo_mật_khẩu_gửi_tới_email
		String randomPassword = UUID.randomUUID().toString().substring(0, 6);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(randomPassword);
		user.setPassword(encodedPassword);

		// submit_create_account
		userService.create(user);
		emailService.sendPasswordEmail(user.getEmail(), randomPassword);
		return "login";
	}

	@RequestMapping(value = "getimage/{avatar}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable("avatar") String avatar) {
		if (!avatar.equals("") || avatar != null) {
			try {
				Path fileName = Paths.get("uploads", avatar);
				byte[] buffer = Files.readAllBytes(fileName);
				ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
				return ResponseEntity.ok().contentType(MediaType.parseMediaType("image/png")).body(byteArrayResource);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.badRequest().build();
	}

}
