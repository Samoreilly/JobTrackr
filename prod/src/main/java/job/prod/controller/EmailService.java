/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.controller;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author samor
 */
@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    public final ThreadPoolTaskExecutor taskExecutor;
    
    public EmailService(ThreadPoolTaskExecutor taskExecutor){
        this.taskExecutor = taskExecutor;
    }
    @Async
    public void getEmail(String email) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();//switched to mimemessage because i couldnt use simplemailmessages with html content
        MimeMessageHelper mime = new MimeMessageHelper(message,true);
        System.out.println("[ASYNC THREAD] Running on: " + Thread.currentThread().getName());

    String emailContent = "<!DOCTYPE html>" +
    "<html lang=\"en\">" +
    "<head>" +
    "<meta charset=\"UTF-8\">" +
    "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
    "<title>Password Reset</title>" +
    "</head>" +
    "<body style=\"background-color: #f4f4f4; padding: 20px; text-align: center;\">" +
    "<div style=\"background: #ffffff; padding: 20px; border-radius: 10px; width: 80%; max-width: 400px; margin: auto;\">" +
        "<img src=\"https://i.imgur.com/gELcCLN.jpeg\" width=\"50\" height=\"50\" alt=\"Book\">" +
        "<p>Your password reset code is:</p>" +
        "<p style=\"font-size: 24px; font-weight: bold; background: #cccfca; padding: 10px; border-radius: 5px;\">" +
            "12345" + 
        "</p>" +
        "<p><strong>From BetterStudy</strong></p>" +
    "</div>" +
    "</body>" +
    "</html>";            
        
        


        
        mime.setFrom("teamproject23756575@gmail.com");
        mime.setTo(email);
        mime.setSubject("Your reset code");    
        mime.setText(emailContent,true);
        
        mailSender.send(message);     
        System.out.println("sent22");
        
    }    
    
}
