package com.spring.backtracking1.mailtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;

import com.spring.backtracking1.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RunWith(MockitoJUnitRunner.class)
public class MailTest {

    @InjectMocks
    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Captor
    private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

    @Test
    public void testSendSimpleMail_Success() throws MessagingException {
        // Arrange
        String email = "test@example.com";
        String expectedBody = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"url\">Change my password</a></p>";

        // Act
        String result = emailService.sendSimpleMail(email);

        // Assert
        assertEquals("MAIL_SENT_SUCCESSFULLY...", result);
		/*
		 * verify(javaMailSender, times(1)).send(mimeMessageCaptor.capture());
		 * 
		 * MimeMessage capturedMessage = mimeMessageCaptor.getValue();
		 * assertEquals(sender, capturedMessage.getFrom()[0].toString());
		 * assertEquals(email, capturedMessage.getAllRecipients()[0].toString());
		 * assertEquals("Here's the link to reset your password",
		 * capturedMessage.getSubject());
		 * 
		 * String actualBody = ((MimeMultipart)
		 * capturedMessage.getContent()).getBodyPart(0).getContent().toString();
		 * assertEquals(expectedBody, actualBody);
		 */
    }

    @Test
    public void testSendSimpleMail_Exception() throws MessagingException {
        // Arrange
        String email = "test@example.com";
        doThrow(new MailSendException("Mail sending failed")).when(javaMailSender).send(any(MimeMessage.class));

        // Act
        String result = emailService.sendSimpleMail(email);

        // Assert
        assertEquals("Error while Sending Mail", result);
    }
}
