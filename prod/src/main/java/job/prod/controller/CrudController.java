/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.controller;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import job.prod.entity.ApplicationDTO;
import job.prod.entity.CompareDTO;
import job.prod.entity.ComparisonResult;
import job.prod.entity.Crud;
import job.prod.repo.Applications;
import job.prod.repo.ApplicationsRepo;
import job.prod.repo.User;
import job.prod.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author samor
 */
@RequestMapping("/api")
@RestController
public class CrudController {
    
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private ApplicationsRepo appRepo;

    @Autowired
    private HttpSession session;
    
    @Autowired
    private Crud crud;
    
    @Autowired
    private Compare co;
    
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String username, @RequestParam String password ){
//        
//        
//        return crud.login(username, email, password);
//    }
    
//    @PostMapping("/signup")
//    public ResponseEntity<String> signup(@RequestParam String email, @RequestParam String username, @RequestParam String password ){
//        
//     return crud.signup(username, email, password);
//    }
//    
    @GetMapping("/id")
    public ResponseEntity<?> currentSession(HttpSession session){
        
        Object id = session.getAttribute("id");
    if (id == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
    }

    Optional<User> user = userRepo.findById((Long) id);
    return user.map(ResponseEntity::ok)
               .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());        
    }
    
    @PostMapping("/application")
    public ResponseEntity<String> application(@RequestBody ApplicationDTO form){ 
        
//        session.setAttribute("id", 1L);
        Long userId = (Long) session.getAttribute("id");
        
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        Applications app = new Applications();
        app.setUser(user);
        app.setJob(form.getJob());
        app.setPosition(form.getPosition());
        app.setStatus(form.getStatus());
        app.setDescription(form.getDescription());
        app.setDate(LocalDateTime.now());
        app.setInterviewDate(form.getInterviewDate());

        appRepo.save(app);

        return ResponseEntity.ok("Application received");
    }
    @GetMapping("/appdata")
    public List<Applications> getAppData(){
        
        session.setAttribute("id", 1L);
        Long id = (Long) session.getAttribute("id");
        

        return appRepo.findApplicationsByUser_Id(id);
        
      
    }
    @PostMapping("/editapp")
    public ResponseEntity<String> editApp(@RequestBody Applications form){
//        session.setAttribute("id", 1L);
        
        Applications appId = appRepo.findApplicationsById(form.getId());     
        
        appId.setJob(form.getJob());
        appId.setPosition(form.getPosition());
        appId.setStatus(form.getStatus());
        appId.setDescription(form.getDescription());
        appId.setInterviewDate(form.getInterviewDate());
        
        appRepo.save(appId);
        
        return ResponseEntity.ok("Application received");
       
        
    }
    @PostMapping("/deleteapp")
    public ResponseEntity<String> deleteApp(@RequestParam String id){
//        session.setAttribute("id", 1L);
//        
        Long trackId = Long.parseLong(id);
        
        Applications appId = appRepo.findApplicationsById(trackId);     
        System.out.println(trackId);
        if(appRepo.existsById(trackId)){
            
            appRepo.delete(appId);            
        }else{
            return ResponseEntity.badRequest().body("Doesnt exist");
        }

        
        return ResponseEntity.ok("Application received");
       
    }   
    @PostMapping("/compareresume")
    public ResponseEntity<?> compareResume(
            @RequestParam String jobDesc,
            @RequestParam MultipartFile file) {
        try {
            String resumeText = co.parseResume(jobDesc, file);
            ComparisonResult result = co.extractKeywords(resumeText);

            Map<String, Object> response = new HashMap<>();
            response.put("score", result.getScore());
            response.put("missedKeywords", result.getMissedKeywords());
            response.put("matchedKeywords", result.getMatchedWords());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/haspaid")
    public ResponseEntity<Boolean> hasUserPaid(){
        
        Long userId = (Long) session.getAttribute("id");
        
        if(userId == null){
            return ResponseEntity.ok(false);
        }
        
        
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("-<,,,,,,,,,,>........"+ user.hasPaid());
        return ResponseEntity.ok(user.hasPaid());
        
    }

            
    
}
