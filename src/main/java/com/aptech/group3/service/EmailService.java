package com.aptech.group3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.InternetAddress;
import java.util.Properties;
@Service
public class EmailService {
//	@Autowired
//    private JavaMailSender emailSender;
//	
//	public EmailService(JavaMailSender emailSender) {
//		this.emailSender = emailSender;
//	}
//	public void sendMessage(String to, String subject, String text) {
//		try {
//			SimpleMailMessage message = new SimpleMailMessage();
//			message.setFrom("nguyenthaithanh101104@gmail.com");
//			message.setTo(to);
//			message.setSubject(subject);
//			message.setText(text);
//			this.emailSender.send(message);
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
	public boolean sendEmail(String subject, String message, String to) {
		boolean f = false;
		String from = "nguyenthaithanh101104@gmail.com";
		String host = "smtp.gmail.com";
		Properties properties = System.getProperties();
		System.out.println("PROPERTIES"+ properties);
		
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("nguyenthaithanh101104@gmail.com", "sjem bdsa epdz dvue");
			}
		});
		
		session.setDebug(true);
		
		MimeMessage m = new MimeMessage(session);
		try {
			
			m.setFrom(from);
			
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			m.setSubject(subject);
			
			m.setText(message);
			
			Transport.send(m);
			System.out.println("Sent success..........");
			f=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return f;
	}
}


