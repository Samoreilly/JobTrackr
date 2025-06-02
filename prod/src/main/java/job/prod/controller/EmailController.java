/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samor
 */
@RequestMapping("/api")
@RestController
public class EmailController {
    
    @Autowired
    private EmailService es;
    
    
    @PostMapping("/email")
    public ResponseEntity<String> sendEmail(@RequestParam String email) throws MessagingException{
        System.out.println("sent111");
        es.getEmail(email);
        return ResponseEntity.ok("Sent");
    }
    
}
