package com.aptech.group3.serviceImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aptech.group3.Dto.MarkApiDto;
import com.aptech.group3.Dto.MarkDetailDto;
import com.aptech.group3.Dto.MarkSubjectCreateDto;
import com.aptech.group3.Dto.MarkSubjectDto;
import com.aptech.group3.Repository.ClassForSubjectRepository;
import com.aptech.group3.Repository.MarkSubjectRepository;
import com.aptech.group3.Repository.StudentClassRepository;
import com.aptech.group3.Repository.SubjectRepository;
import com.aptech.group3.Repository.UserRepository;
import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.MarkSubject;
import com.aptech.group3.entity.Subject;
import com.aptech.group3.entity.User;
import com.aptech.group3.service.MarkSubjectService;

import jakarta.servlet.ServletOutputStream;

@Service
public class MarkSubjectServiceImql implements MarkSubjectService  {
	
	
	@Autowired private MarkSubjectRepository repo;
	@Autowired private ClassForSubjectRepository classRepository;
	@Autowired private UserRepository userRepo;
	@Autowired private SubjectRepository subjectRepo;
	@Autowired private MarkSubjectRepository markRepo;
	@Autowired private StudentClassRepository studentRepo;
	
	
	
	public List<MarkApiDto> getListMarkDto(Long studentId){
		
		
		 List<MarkApiDto> data= studentRepo.findByStudent_Id(studentId).stream().map(
				e->{ MarkApiDto dto = new MarkApiDto();
				
				dto.setClassName(e.getClassforSubject().getDescription());
				dto.setSubjectName(e.getClassforSubject().getSubject().getName());
				dto.setTeacherName(e.getClassforSubject().getTeacher().getName());
				dto.setClassStatus(e.getClassforSubject().getStatus());
				dto.setClassId(e.getClassforSubject().getId());
				List<Float> markArray = new ArrayList<Float>();
				List<MarkSubject> check= markRepo.findByUserIdAndClassForSubjectId(studentId, e.getClassforSubject().getId());
				
				if(check.size()==3) {
				
					check.forEach(c->{
						if(c.getStyle().equals("normalMark")) {
							markArray.add(c.getMark());
						}
						if(c.getStyle().equals("middleMark")) {
							markArray.add(c.getMark());
						}
						if(c.getStyle().equals("finalMark")) {
							markArray.add(c.getMark());
						}
						
					});
					
					dto.setFinalMark((float) (markArray.get(0)*0.2 +markArray.get(1)*0.3 +markArray.get(2)*0.5));
				}
				 return dto;}
				
			
				 ).toList();
		 
			return data;
	}
	
	
	public MarkDetailDto getMarkDetailDto( Long studentId,Long ClassId) {
		
		MarkDetailDto dto = new MarkDetailDto();
		List<MarkSubject> check = new ArrayList<MarkSubject>();
		
		 check= markRepo.findByUserIdAndClassForSubjectId(studentId,ClassId);
		 if(check.size()!=0) {
				check.forEach(c->{
					
					if(c.getStyle().equals("normalMark")) {
						dto.setNormalMark(c.getMark());
					}
					if(c.getStyle().equals("middleMark")) {
						dto.setMiddleMark(c.getMark());
					}
					if(c.getStyle().equals("finalMark")) {
						dto.setFiinalMark(c.getMark());
					}
					
				});	
				
				MarkApiDto markdto= new MarkApiDto();
				markdto.setClassName(check.get(0).getClassSubject().getName());
				markdto.setClassStatus(check.get(0).getClassSubject().getStatus());
				markdto.setSubjectName(check.get(0).getSubject().getName());
				markdto.setTeacherName(check.get(0).getClassSubject().getTeacher().getName());
				if(check.size()==3) {
					markdto.setFinalMark((float) (dto.getNormalMark()*0.2+ dto.getMiddleMark()*0.3+dto.getFiinalMark()*0.5));
				}
				
				dto.setMarkTotal(markdto);
				
				if(markdto.getFinalMark()!=0) {
					if(markdto.getFinalMark()>0 && markdto.getFinalMark()<4.0) {
						dto.setResult("Not pass");
					}
					if(markdto.getFinalMark()>4.0 && markdto.getFinalMark()<6.5) {
						dto.setResult("Pass");
					}
					if(markdto.getFinalMark()>6.5 && markdto.getFinalMark()<8.0) {
						dto.setResult("Good");
					}
					
					if(markdto.getFinalMark()>8.0) {
						dto.setResult("Excillent");
					}
				}
		 }else {
			 MarkApiDto markdto= new MarkApiDto();
				dto.setMarkTotal(markdto);
				ClassForSubject classData= classRepository.findById(ClassId).orElse(null);
				markdto.setClassName(classData.getName());
				markdto.setClassStatus(classData.getStatus());
				markdto.setSubjectName(classData.getSubject().getName());
				markdto.setTeacherName(classData.getTeacher().getName());
		 }

		
		
		
		return dto;
	}

	
	// new api mark
	public void insertMarks(List<MarkSubjectCreateDto> markSubjectCreateDtoList) {
        List<MarkSubject> markSubjects = new ArrayList<>();

        for (MarkSubjectCreateDto dto : markSubjectCreateDtoList) {
            User user = userRepo.findById(dto.getStudentId()).orElseThrow(() -> new RuntimeException("User not found"));
            ClassForSubject classForSubject = classRepository.findById(dto.getClassId()).orElseThrow(() -> new RuntimeException("Class not found"));
            Subject subject = subjectRepo.findById(dto.getSubjectId()).orElseThrow(() -> new RuntimeException("Subject not found"));

            MarkSubject markSubject = new MarkSubject();
            markSubject.setUser(user);
            markSubject.setClassSubject(classForSubject);
            markSubject.setSubject(subject);
            markSubject.setMark(dto.getMark());
            markSubject.setStyle(dto.getStyle());
            markSubjects.add(markSubject);
        }

        markRepo.saveAll(markSubjects);
    }
	    public List<MarkSubject> getMarksByStudentId(Long studentId) {
	        return repo.findByUserId(studentId);
	    }	
	    
	    public List<ClassForSubject> getClassForSubjectBySubjectId(Long subjectId) {
	        return classRepository.findBySubjectId(subjectId);
	    }
	    
	    public MarkSubject getMarkSubjectById(Long id) {
	        return repo.findById(id).orElse(null);
	    }

	    public List<MarkSubject> getListMarkSubjectByClassId(Long classId) {
	        return repo.findByClassId(classId);
	    }
	    public List<MarkSubject> getListMarkSubjectByStudentIdAndClassId(Long userId, Long classId) {
	        return repo.findByUserIdAndClassForSubjectId(userId, classId);
	    }
	   
	    public void exportMarkSubjectToExcel(List<MarkSubject> markSubjects, String filePath) throws IOException {
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet("Mark Subjects");

	        // Tạo tiêu đề cho các cột
	        Row headerRow = sheet.createRow(0);
	        headerRow.createCell(0).setCellValue("ID");
	        headerRow.createCell(1).setCellValue("Student Name");
	        headerRow.createCell(2).setCellValue("Subject Name");
	        headerRow.createCell(3).setCellValue("Mark");

	        // Đổ dữ liệu vào các dòng
	        int rowNum = 1;
	        for (MarkSubject markSubject : markSubjects) {
	            Row row = sheet.createRow(rowNum++);
	            row.createCell(0).setCellValue(markSubject.getId());
	            row.createCell(1).setCellValue(markSubject.getUser().getName());
	            row.createCell(2).setCellValue(markSubject.getSubject().getName());
	            row.createCell(3).setCellValue(markSubject.getMark());
	        }

	        // Ghi workbook ra file
	        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
	            workbook.write(fileOut);
	        }
	        // Giải phóng tài nguyên
	        workbook.close();
	    }
	    public void exportToExcel(List<MarkSubject> marks, ServletOutputStream outputStream) {
			Workbook workbook = new XSSFWorkbook();
			Sheet sheet = workbook.createSheet("Marks");
			int rowNum = 0;
			// Tạo dòng cho tiêu đề "Danh sách học sinh"
			Row titleRow = sheet.createRow(rowNum++);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellValue("List mark subject");
			titleCell.setCellStyle(getCenteredCellStyle(workbook));
			// Gộp các ô thành một ô duy nhất cho tiêu đề "Danh sách học sinh"
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4)); // Từ cột 0 đến cột 5

			// Tạo dòng cho header
			Row headerRow = sheet.createRow(rowNum++);
			String[] headers = { "Id", "Name", "Subject", "Mark"};
			for (int i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
			}

			// Thêm dữ liệu từ danh sách users
			for (MarkSubject mark : marks) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(mark.getId());
				row.createCell(1).setCellValue(mark.getUser().getName());
				row.createCell(2).setCellValue(mark.getClassSubject().getName());
				row.createCell(3).setCellValue(mark.getMark());
				// Thêm các cột "Mark" và "Sign" ở đây nếu có giá trị tương ứng từ đối tượng
				// User
			}

			try {
				workbook.write(outputStream);
				workbook.close();
			} catch (IOException e) {
				// Xử lý ngoại lệ
				e.printStackTrace();
			}
		}
		private CellStyle getCenteredCellStyle(Workbook workbook) {
			CellStyle style = workbook.createCellStyle();
			style.setAlignment(HorizontalAlignment.CENTER); // Đặt căn giữa cho văn bản
			style.setVerticalAlignment(VerticalAlignment.CENTER); // Đặt căn giữa theo chiều dọc
			return style;
		}

}