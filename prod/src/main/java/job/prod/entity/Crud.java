/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.entity;

import jakarta.servlet.http.HttpSession;
import job.prod.repo.User;
import job.prod.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author samor
 */
@Service
public class Crud {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepo userRepo;  
    
    @Autowired
    private HttpSession session;
       
    private boolean member;
    
    public ResponseEntity<String> login(String username, String email,String password){
        

        
        if(email == null || username == null || password == null){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Fill in the empty fields");                  
        }
        
        User user = userRepo.findByUsernameIgnoreCase(username).orElse(null);      
        
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("Invalid credentails");
        }    
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Invalid");
  
    }
//    public ResponseEntity<String> signup(String username, String email, String password){
//        
//        if(email == null || username == null || password == null){
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body("Please fill in the empty fields");
//            
//        }
//        
//        if(userRepo.existsByEmail(email)){
//            
//           return ResponseEntity
//                   .status(HttpStatus.CONFLICT)
//                   .body("Email or username already exists");
//        }
//        
//        if(!checkPassword(password)){
//            
//            return ResponseEntity
//                    .status(HttpStatus.CONFLICT)
//                    .body("Your password must contain a symbol e.g. !, &, %, ], {");
//            
//        }
//        
//        String encodedPassword = passwordEncoder.encode(password);
//        
//        User userInfo = new User(username,email,encodedPassword,member);
//        userRepo.save(userInfo);
//        
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body("Enter valid details");
//                   
//    }
    
    public boolean checkPassword(String password){//used to check if password meets security criteria
        boolean validPassword = false;
        String[] symbols = {
            "!", "#", "$", "%", "&", "(", ")", "*", "+", ",", "-", ".", "?", "@", "[", "]", "^", "_", "{", "|", 
            "}", "~"
        };
        for(int i = 0; i < password.length(); i++){
            
           for(int j = 0;j < symbols.length;j++){         
                if(password.charAt(i) == symbols[j].charAt(0)){
                      return true;          
                }
           } 
           
       }
       return false; 
    } 
    
    
    
    
    
    
    
    
    
//        public ResponseEntity<String> login(String username, String email,String password){
//        
//
//        
//        if(email == null || username == null || password == null){
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body("Fill in the empty fields");                  
//        }
//        
//        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//        User user = userRepo.findByUsernameIgnoreCase(username);      
//        
//        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
//            return ResponseEntity
//                    .status(HttpStatus.FORBIDDEN)
//                    .body("Invalid credentails");
//        }    
//
//        if (userDetails == null) {
//            return ResponseEntity
//                    .status(HttpStatus.FORBIDDEN)
//                    .body("Invalid credentails");
//        }        
//        UsernamePasswordAuthenticationToken authToken = 
//        new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities());//compares users details
//
//        try {
//            authenticationManager.authenticate(authToken);
//
//            SecurityContextHolder.getContext().setAuthentication(authToken); // this sets the users session
//
//            session.setAttribute("loggedIn", true);
//            session.setAttribute("id", user.getId());
//
//            return ResponseEntity
//                    .status(HttpStatus.ACCEPTED)
//                    .body("Successfully logged in");
//            
//            
//        } catch (Exception e) {
//            session.setAttribute("loggedIn", false);
//            return ResponseEntity
//                    .status(HttpStatus.FORBIDDEN)
//                    .body("Enter valid credentials");
//        }     
//  
//    }
    
}
