package com.aptech.group3.Controller;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.aptech.group3.Dto.CashDto;
import com.aptech.group3.Dto.ClassStatus;

import com.aptech.group3.entity.ClassForSubject;
import com.aptech.group3.entity.Paymenttt;
import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.entity.User;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.ClassForSubjectService;
import com.aptech.group3.service.PaymentService;

import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.UserService;
import com.aptech.group3.service.VNPayService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PaymentController {
	@Autowired
	private StudentClassService service;
	@Autowired
	private PaymentService paymentservice;
	 @Autowired
	    private VNPayService vnPayService;
	 @Autowired
	 private UserService userservice;
	 @Autowired
	 private ClassForSubjectService classforsubjectservice;

	
	@GetMapping("/web/payment")
	public String payment(Model model,@AuthenticationPrincipal CustomUserDetails currentUser)
	{
		  List<StudentClass> classs = service.findByStudentIdAndStatus(currentUser.getUserId(), ClassStatus.UNPAID);
	
		
		  model.addAttribute("classes",classs);
		return "page/paypal/index";
	}
	//them moi
	@GetMapping("/admin/payment/search")
    public String search(@RequestParam("name") String name, Model model) {
        List<StudentClass> studentClasses = service.findStudentClassesByUserName(name);
        model.addAttribute("offline", studentClasses);
        return "page/paypal/paymentoffline"; // Ensure this matches your template name
    }
	
	@GetMapping("/web/payment/details")
	public String details(Model model, @AuthenticationPrincipal CustomUserDetails currentUser,
	                      @RequestParam(name = "page", defaultValue = "1") int page) {
	    int pageSize = 10; // Number of records per page
Pageable pageable = PageRequest.of(page - 1, pageSize);
	    Page<Paymenttt> paymentPage = paymentservice.findByStudentId(currentUser.getUserId(), pageable);

	    model.addAttribute("paymentPage", paymentPage);
	    model.addAttribute("currentPage", page);

	    return "page/paypal/details";
	}
	
		@GetMapping("/admin/adminpayment")
		public String adminpayment(Model model, @RequestParam(name = "page", defaultValue = "1") int page, @RequestParam(required = false) String code) {
		    int pageSize = 10; // Number of records per page
		    Pageable pageable = PageRequest.of(page - 1, 10);
		    Page<Paymenttt> paymentPage;
		    boolean noResults = false;
	
		    if (code != null && !code.isEmpty()) {
		        paymentPage = paymentservice.findByUserCode(code, pageable);
		        if (paymentPage.isEmpty()) {
		            noResults = true;
		        }
		    } else {
		        paymentPage = paymentservice.findAll(pageable);
		    }
	
		    model.addAttribute("paymentPage", paymentPage);
		    model.addAttribute("currentPage", page);
		    model.addAttribute("code", code); // để giữ lại giá trị code trong input search
		    model.addAttribute("noResults", noResults);
	
		    return "page/paypal/adminpayment";
		}
	@GetMapping("/admin/payment/searchh")
    public String searchh(@RequestParam("name") String name, Model model) {
        List<StudentClass> studentClasses = service.findStudentClassesByUserName(name);
        model.addAttribute("offline", studentClasses);
        return "page/paypal/adminpayment"; // Ensure this matches your template name
    }

	@PostMapping("/web/submitOrder")
	public String submitOrder(@RequestParam("amount") int orderTotal,
	                          @RequestParam("orderInfo") String orderInfo, @RequestParam("selectedItems") String selectedItems,
	                          HttpServletRequest request) {

	        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+"/web";
	        orderInfo=selectedItems;
	        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);        
	        return "redirect:" + vnpayUrl;	
	}

	@GetMapping("/web/vnpay-payment")
	public String GetMapping(HttpServletRequest request, Model model ,@AuthenticationPrincipal CustomUserDetails currentUser) 
	{
	    int paymentStatus = vnPayService.orderReturn(request);
	    
	    String orderInfo = request.getParameter("vnp_OrderInfo");
	    String paymentTime = request.getParameter("vnp_PayDate");
	    String transactionId = request.getParameter("vnp_TransactionNo");
	    String totalPrice = request.getParameter("vnp_Amount");
	    int totalPriceint=Integer.parseInt(totalPrice);
	    
	    	List<Long> idList = Arrays.stream(orderInfo.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
	    
	    System.out.println("idList: " + idList);
	    model.addAttribute("orderId", orderInfo);
model.addAttribute("totalPrice", totalPriceint/100);
	    model.addAttribute("paymentTime", paymentTime);
	    model.addAttribute("transactionId", transactionId);
	    if(paymentStatus==1)
	    {
	    	 User student = userservice.findById(currentUser.getUserId());
	    	for (Long classId : idList) 
	    	{
	            ClassForSubject classForSubject = classforsubjectservice.findById(classId);
	            Paymenttt payment = new Paymenttt();
	            int classCredit = classForSubject.getSubject().getCredit();
	            payment.setStudent(student);
	            payment.setClassforSubject(classForSubject);
	            int classPayment = classCredit * 150 *25000;
	            payment.setCash(classPayment);
	            payment.setPayments("VNPay");
	            payment.setDate(new Date());
	            paymentservice.save(payment);
	        }
	    	List<Long> listData=service.getListStudentRegistered(currentUser.getUserId(), idList);
	    	service.updateItemsStatusToPayment(listData);
	    }
	    return paymentStatus == 1 ? "ordersuccess" : "orderfail";
	}
	
	@GetMapping("/admin/paymentoffline")
	public String paymentoffline (Model model,@RequestParam(name = "codee", required = false) String se) {
		List<StudentClass> classss= new ArrayList<StudentClass>();
		CashDto dto=new CashDto();
		if (se != null) {
			classss = service.findStudentClassesByUserName(se);
			if(classss.size()!=0)
			{
				dto.setUserId(classss.get(0).getStudent().getId());
			}
	        if (classss.isEmpty()) {
	            model.addAttribute("noResults", true);
	        }
	    }
		
		
		
		model.addAttribute("offline",classss);
		
		model.addAttribute("dto",dto);
		return "page/paypal/paymentoffline";
	}
	 @PostMapping("/admin/payment/updateStatus")
	    public void updateStatus(@ModelAttribute CashDto dto , HttpServletResponse response) 
	 {
	        System.out.println("selectedItems"  +dto);

	    	 User student = userservice.findById(dto.getUserId());
	    	
	    	for (Long classId : dto.getSubjectId()) 
	    	{
	            ClassForSubject classForSubject = classforsubjectservice.findById(classId);
	            Paymenttt payment = new Paymenttt();
	            int classCredit = classForSubject.getSubject().getCredit();
	            payment.setStudent(student);
	            payment.setClassforSubject(classForSubject);
	            int classPayment = classCredit * 3000000;
	            payment.setCash(classPayment);
	            payment.setPayments("Ofline");
	            payment.setDate(new Date());
	            paymentservice.save(payment);
	        }
	    	List<Long> listData=service.getListStudentRegistered(dto.getUserId(), dto.getSubjectId());
	    	service.updateItemsStatusToPayment(listData);
	    	
	    	try {
	            ByteArrayOutputStream baos = createPDF(dto); // Phương thức để tạo PDF
	            response.setContentType("application/pdf");
response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");
	            response.getOutputStream().write(baos.toByteArray());
	            response.getOutputStream().flush();
	        } catch (IOException | DocumentException e) {
	            e.printStackTrace(); // Xử lý lỗi tạo file PDF
	        }

	            
	        
	  }
	 private ByteArrayOutputStream createPDF(CashDto dto) throws DocumentException, MalformedURLException, IOException {
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         Document document = new Document();
         PdfWriter.getInstance(document, baos);

         document.open();
         Image logo = Image.getInstance("uploads/logo.png"); // Ensure the path to the image is correct
         logo.setAlignment(Image.ALIGN_CENTER); // Optional: Align the image to the center
         document.add(logo);
         document.add(new Paragraph("Invoice Details:"));
         // Thêm thông tin từ DTO vào hóa đơn
         document.add(new Paragraph("User ID: " + dto.getUserId()));
         document.add(new Paragraph("Selected Items: " + dto.getSubjectId()));
         // Thêm các thông tin khác về thanh toán, ví dụ như tổng tiền, ngày thanh toán, v.v.
         document.close();

         return baos;
	 }
	 
}