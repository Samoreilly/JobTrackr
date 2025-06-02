package job.prod.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import job.prod.entity.ApplicationDTO;
import job.prod.entity.ComparisonResult;
import job.prod.entity.Crud;
import job.prod.repo.Applications;
import job.prod.repo.ApplicationsRepo;
import job.prod.repo.User;
import job.prod.repo.UserRepo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api")
@RestController
public class CrudController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ApplicationsRepo appRepo;

    @Autowired
    private Crud crud;

    @Autowired
    private Compare co;

    @GetMapping("/id")
    public ResponseEntity<?> currentSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Long id = (Long) session.getAttribute("id");
        Optional<User> user = userRepo.findById(id);

        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/application")
    public ResponseEntity<String> application(@RequestBody ApplicationDTO form, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }

        Long id = (Long) session.getAttribute("id");
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

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
    public ResponseEntity<?> getAppData(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Long id = (Long) session.getAttribute("id");
        return ResponseEntity.ok(appRepo.findApplicationsByUser_Id(id));
    }

    @PostMapping("/editapp")
    public ResponseEntity<String> editApp(@RequestBody Applications form, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Applications appId = appRepo.findApplicationsById(form.getId());
        if (appId == null) {
            return ResponseEntity.badRequest().body("Application not found");
        }

        appId.setJob(form.getJob());
        appId.setPosition(form.getPosition());
        appId.setStatus(form.getStatus());
        appId.setDescription(form.getDescription());
        appId.setInterviewDate(form.getInterviewDate());

        appRepo.save(appId);
        return ResponseEntity.ok("Application updated");
    }

    @PostMapping("/deleteapp")
    public ResponseEntity<String> deleteApp(@RequestParam String id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        Long trackId;
        try {
            trackId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid ID format");
        }

        Applications appId = appRepo.findApplicationsById(trackId);
        if (appId == null || !appRepo.existsById(trackId)) {
            return ResponseEntity.badRequest().body("Application does not exist");
        }

        appRepo.delete(appId);
        return ResponseEntity.ok("Application deleted");
    }

    @PostMapping("/compareresume")
    public ResponseEntity<?> compareResume(@RequestParam String jobDesc, @RequestParam MultipartFile file) {
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
    public ResponseEntity<?> hasUserPaid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
            return ResponseEntity.ok(false);
        }

        Long userId = (Long) session.getAttribute("id");
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(user.hasPaid());
    }
}
