/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.controller;

import java.io.IOException;
import job.prod.entity.Crud;
import job.prod.entity.Resume;
import job.prod.entity.Resume.ResumeScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author samor
 */
@RestController
public class ResumeController {
    
    @Autowired
    private Resume res;
    
    @PostMapping("/resume")
    public ResponseEntity<ResumeScore> getResume(@RequestParam("file") MultipartFile file) throws IOException{
        ResumeScore result = res.scoreResume(file);
        return ResponseEntity.ok(result);
        
    }
    
}
