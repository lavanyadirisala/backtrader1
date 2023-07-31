package com.spring.backtracking1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String sender;
	
	public String sendSimpleMail(String email) {		
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			
			//sample url needs to be entered 
			String mailbody = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
					+ "<p>Click the link below to change your password:</p>"
					+ "<p><a href=\"url\">Change my password</a></p>";
			
			helper.setFrom(sender);
			helper.setTo(email);
			helper.setText(mailbody, true);
			helper.setSubject("Here's the link to reset your password");
			
			// Sending the mail
			javaMailSender.send(message);
			return "MAIL_SENT_SUCCESSFULLY...";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			System.out.println(e.getMessage());
			return "Error while Sending Mail";
		}
	}

}
