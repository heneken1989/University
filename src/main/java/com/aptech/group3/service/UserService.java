package com.aptech.group3.service;



import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aptech.group3.Dto.UserCreateDto;
import com.aptech.group3.Dto.UserDto;
import com.aptech.group3.entity.Field;
import com.aptech.group3.entity.User;

import jakarta.servlet.ServletOutputStream;
@Service
public interface UserService {
	public Page<User> findByRole(String role, Pageable pageable);
	public void create(UserCreateDto dto);
	public UserDetails loadUserByUserid(Long id);
	public User findById(Long id);
	public void update(UserCreateDto dto);
	public int countStudents();
	public int countTeachers();
	public int countEmployees();
	public List<User> listAll();
	public void exportToExcel(List<User> users, ServletOutputStream outputStream);
	public List<User> getUsersByRole(String role);
	public List<User> readUsersFromExcel(MultipartFile file) throws IOException;
	public void saveUsers(List<User> users);
	public void saveAndEncoderPassword(List<User> users);
	public List<Field> findFieldsByUserId(Long userId);
	public List<String> getFieldNamesByUserId(Long userId);
	public boolean checkIfEmailExists(String email);
}
