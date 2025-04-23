/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.controller;

import job.prod.entity.Crud;
import job.prod.repo.User;
import job.prod.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samor
 */
@RestController
public class CrudController {
    
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private Crud crud;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String username, @RequestParam String password ){
        
        
        return crud.login(username, email, password);
    }
    
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestParam String email, @RequestParam String username, @RequestParam String password ){
        
     return crud.signup(username, email, password);
    }
            
    
}
