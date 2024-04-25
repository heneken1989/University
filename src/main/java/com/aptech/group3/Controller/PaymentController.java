package com.aptech.group3.Controller;



import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



import com.aptech.group3.entity.StudentClass;
import com.aptech.group3.model.CustomUserDetails;
import com.aptech.group3.service.StudentClassService;
import com.aptech.group3.service.VNPayService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PaymentController {
	@Autowired
	private StudentClassService service;
	 @Autowired
	    private VNPayService vnPayService;
	 
	
	@GetMapping("/payment")
	public String payment(Model model,@AuthenticationPrincipal CustomUserDetails currentUser)
	{
		  List<StudentClass> classs = service.findByStudentIdAndStatus(currentUser.getUserId(), "Waiting");
	
		
		  model.addAttribute("classes",classs);
		return "page/paypal/index";
	}
	
	@PostMapping("/submitOrder")
	public String submitOrder(@RequestParam("amount") int orderTotal,
	                          @RequestParam("orderInfo") String orderInfo, @RequestParam("selectedItems") String selectedItems,
	                          HttpServletRequest request) {
	    
		
	        
	        // Tiếp tục xử lý và chuyển hướng
	        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	        orderInfo=selectedItems;
	        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
	        
	        return "redirect:" + vnpayUrl;
	    
		
	}

	@GetMapping("/vnpay-payment")
	public String GetMapping(HttpServletRequest request, Model model) {
	    int paymentStatus = vnPayService.orderReturn(request);
	    
	    String orderInfo = request.getParameter("vnp_OrderInfo");
	    String paymentTime = request.getParameter("vnp_PayDate");
	    String transactionId = request.getParameter("vnp_TransactionNo");
	    String totalPrice = request.getParameter("vnp_Amount");
	    
	    
//	    // Chuyển đổi chuỗi thành danh sách các số nguyên dài
	    
	    
	    System.out.println("orderInfo: " + orderInfo);
	   
	    	
	    	List<Long> idList = Arrays.stream(orderInfo.split(","))
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
	    
	    System.out.println("selectedItemsString: " + idList);
	    model.addAttribute("orderId", orderInfo);
	    model.addAttribute("totalPrice", totalPrice);
	    model.addAttribute("paymentTime", paymentTime);
	    model.addAttribute("transactionId", transactionId);
	    if(paymentStatus==1)
	    {
	    	service.updateItemsStatusToPayment(idList);
	    }
	    return paymentStatus == 1 ? "ordersuccess" : "orderfail";
	}
}
