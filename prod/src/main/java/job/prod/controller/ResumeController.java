/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.controller;

import java.io.IOException;
import job.prod.entity.Crud;
import job.prod.entity.JobMatch;
import job.prod.entity.Resume;
import job.prod.entity.UserScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author samor
 */
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ResumeController {
    
    @Autowired
    public Resume res;
    
    @Autowired
    public JobMatch js;
    
    private byte[] storedFileBytes;
    private String storedFileName;
    private String storedFileContentType;
    
    private String resText = null;
    private MultipartFile storedFile;
    
    @PostMapping("/resume")
    public ResponseEntity<UserScore> getResume(@RequestParam("file") MultipartFile file) throws IOException{
        
        storedFileBytes = file.getBytes();
        storedFileName = file.getOriginalFilename();
        storedFileContentType = file.getContentType();
        
        UserScore result = res.scoreResume(file);
        String nlpText = js.parseResume(file);
        resText = nlpText;
        int score = js.extractKeywords(nlpText);
        result.setScore(score);
        System.out.println("***************************************_________________________________**************************************************************"+score);
        return ResponseEntity.ok(result);
        
    }
    public String processResume(MultipartFile file) throws IOException {
        
    String rawText = res.parseResume(file);
    if(rawText != null){
        rawText = res.parsePDF(file);
    }
    return "";
    }
    @GetMapping("/text")
    public ResponseEntity<String> getText() throws IOException{
        if (storedFileBytes == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No resume file uploaded");
        }

        MultipartFile file = new InMemoryMultipartFile(storedFileName, storedFileBytes, storedFileContentType);

        String text = res.parseResume(file);
    return ResponseEntity.ok(text);
    }
    @PostMapping("/rewrite")
    public ResponseEntity<?> rewrite(@RequestParam MultipartFile file) {
        try {
            String resumeText = res.parseResume(file);
            System.out.println(resumeText);
            return ResponseEntity.ok(resumeText);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }    
    
    
}
