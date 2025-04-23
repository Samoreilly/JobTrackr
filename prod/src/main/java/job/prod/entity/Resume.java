package job.prod.entity;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class Resume {

    private static TokenizerME tokenizer;
    private static POSTaggerME posTagger;
    private static NameFinderME nameFinder;
    
    // Define keywords to look for in resumes, categorized by field
    private static final Map<String, List<String>> SKILL_KEYWORDS = new HashMap<>();
    
    static {
        // Initialize keyword categories
        SKILL_KEYWORDS.put("programming", Arrays.asList("java", "python", "javascript", "c++", "c#", "ruby", "php", "scala", "kotlin", "swift"));
        SKILL_KEYWORDS.put("data", Arrays.asList("sql", "mysql", "postgresql", "mongodb", "database", "analytics", "visualization", "hadoop", "spark", "tableau"));
        SKILL_KEYWORDS.put("web", Arrays.asList("html", "css", "react", "angular", "vue", "nodejs", "express", "django", "flask", "spring"));
        SKILL_KEYWORDS.put("cloud", Arrays.asList("aws", "azure", "gcp", "cloud", "docker", "kubernetes", "serverless", "microservices", "devops", "cicd"));
        
        try {
            // Load Tokenizer Model
            InputStream tokenModelIn = new FileInputStream("src/main/resources/models/opennlp-en-ud-ewt-tokens-1.2-2.5.0.bin");
            TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            tokenizer = new TokenizerME(tokenModel);
            tokenModelIn.close();

            // Load POS Tagger Model
            InputStream posModelIn = new FileInputStream("src/main/resources/models/opennlp-en-ud-ewt-pos-1.2-2.5.0.bin");
            POSModel posModel = new POSModel(posModelIn);
            posTagger = new POSTaggerME(posModel);
            posModelIn.close();

            // Load NER Model for Person names
            InputStream nerModelIn = new FileInputStream("src/main/resources/models/en-ner-person.bin");
            TokenNameFinderModel nerModel = new TokenNameFinderModel(nerModelIn);
            nameFinder = new NameFinderME(nerModel);
            nerModelIn.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ResumeScore scoreResume(MultipartFile file) {
        String resumeText;
        try {
            if (file.getOriginalFilename().endsWith(".pdf")) {
                resumeText = parsePdf(file);
            } else if (file.getOriginalFilename().endsWith(".docx")) {
                resumeText = parseDocx(file);
            } else {
                throw new IllegalArgumentException("Unsupported file type");
            }

            // Extract and score different sections
            Map<String, String> sections = extractSections(resumeText);
            return calculateScore(sections);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse resume: " + e.getMessage(), e);
        }
    }

    private String parsePdf(MultipartFile file) throws IOException {
        PDDocument document = PDDocument.load(file.getInputStream());
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();
        return text;
    }

    private String parseDocx(MultipartFile file) throws IOException {
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            StringBuilder sb = new StringBuilder();
            for (XWPFParagraph para : paragraphs) {
                sb.append(para.getText()).append("\n");
            }
            return sb.toString();
        }
    }

    private Map<String, String> extractSections(String resumeText) {
        Map<String, String> sections = new HashMap<>();
        String lowerText = resumeText.toLowerCase();

        // Define section headers
        Map<String, List<String>> sectionHeaders = new HashMap<>();
        sectionHeaders.put("experience", Arrays.asList("experience", "work history", "professional experience", "employment history"));
        sectionHeaders.put("education", Arrays.asList("education", "academic background", "academic history", "qualifications"));
        sectionHeaders.put("skills", Arrays.asList("skills", "technical skills", "core competencies", "expertise"));
        sectionHeaders.put("projects", Arrays.asList("projects", "personal projects", "technical projects"));
        sectionHeaders.put("summary", Arrays.asList("summary", "profile", "objective", "professional summary"));

        // List of all possible headers for finding section boundaries
        List<String> allHeaders = new ArrayList<>();
        for (List<String> headers : sectionHeaders.values()) {
            allHeaders.addAll(headers);
        }

        // Extract each section
        for (Map.Entry<String, List<String>> entry : sectionHeaders.entrySet()) {
            String sectionName = entry.getKey();
            List<String> possibleHeaders = entry.getValue();
            
            // Find the start of this section
            int startIdx = -1;
            String matchedHeader = "";
            for (String header : possibleHeaders) {
                int idx = lowerText.indexOf(header);
                if (idx != -1 && (startIdx == -1 || idx < startIdx)) {
                    startIdx = idx;
                    matchedHeader = header;
                }
            }
            
            if (startIdx != -1) {
                // Find the end of this section (next section start)
                int endIdx = lowerText.length();
                for (String header : allHeaders) {
                    int idx = lowerText.indexOf(header, startIdx + matchedHeader.length());
                    if (idx != -1 && idx < endIdx && !matchedHeader.equals(header)) {
                        endIdx = idx;
                    }
                }
                
                // Extract the section text
                String sectionText = resumeText.substring(startIdx, endIdx).trim();
                sections.put(sectionName, sectionText);
            }
        }
        
        return sections;
    }

    private ResumeScore calculateScore(Map<String, String> sections) {
        ResumeScore score = new ResumeScore();
        
        // Score experience section (max 40 points)
        if (sections.containsKey("experience")) {
            String experienceText = sections.get("experience");
            score.experienceScore = scoreExperience(experienceText);
        }
        
        // Score education section (max 20 points)
        if (sections.containsKey("education")) {
            String educationText = sections.get("education");
            score.educationScore = scoreEducation(educationText);
        }
        
        // Score skills section (max 30 points)
        if (sections.containsKey("skills")) {
            String skillsText = sections.get("skills");
            score.skillsScore = scoreSkills(skillsText);
        } else {
            // If no dedicated skills section, try to extract skills from the entire resume
            StringBuilder fullText = new StringBuilder();
            for (String sectionText : sections.values()) {
                fullText.append(sectionText).append("\n");
            }
            score.skillsScore = (int) Math.round(scoreSkills(fullText.toString()) * 0.8);
        }
        
        // Score projects section (max 10 points)
        if (sections.containsKey("projects")) {
            String projectsText = sections.get("projects");
            score.projectsScore = scoreProjects(projectsText);
        }
        
        // Calculate total score (normalized to 100)
        score.calculateTotalScore();
        
        return score;
    }
    
    private int scoreExperience(String experienceText) {
        int score = 0;
        String lowerText = experienceText.toLowerCase();
        
        // Check for years of experience (max 15 points)
        int yearsOfExperience = extractYearsOfExperience(experienceText);
        score += Math.min(yearsOfExperience * 3, 15);
        
        // Check for job titles (max 10 points)
        List<String> relevantTitles = Arrays.asList("developer", "engineer", "architect", "lead", "manager", "director");
        for (String title : relevantTitles) {
            if (lowerText.contains(title)) {
                score += 2; // 2 points per relevant title
            }
        }
        score = Math.min(score, 10); // Cap at 10 points
        
        // Check for detailed descriptions (max 10 points)
        if (experienceText.length() > 500) score += 5;
        if (experienceText.length() > 1000) score += 5;
        
        // Check for quantifiable achievements (max 5 points)
        Pattern percentPattern = Pattern.compile("\\d+%");
        Pattern numberPattern = Pattern.compile("\\bincreased\\b|\\bimproved\\b|\\breduced\\b|\\bgenerated\\b");
        
        Matcher percentMatcher = percentPattern.matcher(lowerText);
        Matcher numberMatcher = numberPattern.matcher(lowerText);
        
        if (percentMatcher.find()) score += 3;
        if (numberMatcher.find()) score += 2;
        
        return Math.min(score, 40); // Cap at 40 points
    }
    
    private int extractYearsOfExperience(String text) {
        // Basic estimation of years of experience based on date ranges
        int years = 0;
        
        // Look for date patterns like "2015-2020" or "June 2018 - present"
        Pattern dateRangePattern = Pattern.compile("(19|20)\\d{2}[\\s-]+(19|20)\\d{2}|" +
                                                 "(19|20)\\d{2}[\\s-]+present|" +
                                                 "(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]* (19|20)\\d{2}[\\s-]+" +
                                                 "((jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[a-z]* (19|20)\\d{2}|present)");
        
        Matcher matcher = dateRangePattern.matcher(text.toLowerCase());
        
        while (matcher.find()) {
            String dateRange = matcher.group();
            years += estimateYearsInRange(dateRange);
        }
        
        return years;
    }
    
    private int estimateYearsInRange(String dateRange) {
        // Extract years from strings like "2015-2020" or "june 2018 - present"
        dateRange = dateRange.toLowerCase();
        Pattern yearPattern = Pattern.compile("(19|20)\\d{2}");
        Matcher matcher = yearPattern.matcher(dateRange);
        
        List<Integer> years = new ArrayList<>();
        while (matcher.find()) {
            years.add(Integer.parseInt(matcher.group()));
        }
        
        if (years.size() >= 2) {
            return years.get(1) - years.get(0);
        } else if (years.size() == 1 && dateRange.contains("present")) {
            return 2025 - years.get(0); // Current year (2025) minus start year
        }
        
        return 1; // Default to 1 year if pattern is unusual
    }
    
    private int scoreEducation(String educationText) {
        int score = 0;
        String lowerText = educationText.toLowerCase();
        
        // Check for degree level (max 10 points)
        if (lowerText.contains("ph.d") || lowerText.contains("doctorate")) {
            score += 10;
        } else if (lowerText.contains("master") || lowerText.contains("mba") || lowerText.contains("ms ") || lowerText.contains("m.s")) {
            score += 8;
        } else if (lowerText.contains("bachelor") || lowerText.contains("bs ") || lowerText.contains("ba ") || lowerText.contains("b.s") || lowerText.contains("b.a")) {
            score += 6;
        } else if (lowerText.contains("associate") || lowerText.contains("diploma") || lowerText.contains("certificate")) {
            score += 4;
        }
        
        // Check for relevant field of study (max 6 points)
        List<String> relevantFields = Arrays.asList("computer science", "information technology", "software engineering", 
                                                   "data science", "electrical engineering", "computer engineering", 
                                                   "mathematics", "statistics", "information systems");
        
        for (String field : relevantFields) {
            if (lowerText.contains(field)) {
                score += 6;
                break;
            }
        }
        
        // Check for prestigious institutions (max 4 points)
        List<String> prestigiousSchools = Arrays.asList("mit", "stanford", "harvard", "berkeley", "carnegie mellon", 
                                                       "princeton", "oxford", "cambridge", "caltech", "eth zurich");
        
        for (String school : prestigiousSchools) {
            if (lowerText.contains(school)) {
                score += 4;
                break;
            }
        }
        
        return Math.min(score, 20); // Cap at 20 points
    }
    
    private int scoreSkills(String skillsText) {
        int score = 0;
        String lowerText = skillsText.toLowerCase();
        
        // Score based on keyword categories (max 30 points)
        for (Map.Entry<String, List<String>> category : SKILL_KEYWORDS.entrySet()) {
            int categoryScore = 0;
            for (String keyword : category.getValue()) {
                if (lowerText.contains(keyword)) {
                    categoryScore += 2; // 2 points per keyword match
                }
            }
            score += Math.min(categoryScore, 10); // Max 10 points per category
        }
        
        // Check for organization and clarity (max 5 points)
        if (skillsText.contains(",") || skillsText.contains("•") || skillsText.contains("-")) {
            score += 3; // Well-organized list of skills
        }
        
        // Check for skill level indicators (max 5 points)
        if (lowerText.contains("proficient") || lowerText.contains("expert") || 
            lowerText.contains("advanced") || lowerText.contains("intermediate") || 
            lowerText.contains("beginner") || lowerText.contains("familiar")) {
            score += 2;
        }
        
        return Math.min(score, 30); // Cap at 30 points
    }
    
    private int scoreProjects(String projectsText) {
        int score = 0;
        
        // Check for number of projects (max 5 points)
        Pattern projectPattern = Pattern.compile("(?m)^\\s*[•\\-*]|(?:\\d+\\.\\s+)|(?:[A-Z][^\\n.]*:)");
        Matcher projectMatcher = projectPattern.matcher(projectsText);
        
        int projectCount = 0;
        while (projectMatcher.find()) {
            projectCount++;
        }
        
        score += Math.min(projectCount * 2, 5); // 2 points per project, max 5 points
        
        // Check for detailed descriptions (max 5 points)
        if (projectsText.length() > 300) score += 3;
        if (projectsText.length() > 600) score += 2;
        
        return Math.min(score, 10); // Cap at 10 points
    }

    // Inner class to hold resume scores
    public class ResumeScore {
        private int experienceScore = 0;
        private int educationScore = 0;
        private int skillsScore = 0;
        private int projectsScore = 0;
        private int totalScore = 0;
        
        public void calculateTotalScore() {
            totalScore = experienceScore + educationScore + skillsScore + projectsScore;
        }
        
        public int getExperienceScore() {
            return experienceScore;
        }
        
        public int getEducationScore() {
            return educationScore;
        }
        
        public int getSkillsScore() {
            return skillsScore;
        }
        
        public int getProjectsScore() {
            return projectsScore;
        }
        
        public int getTotalScore() {
            return totalScore;
        }
        
        public String getScoreBreakdown() {
            return String.format(
                "Resume Score: %d/100\n" +
                "- Experience: %d/40\n" +
                "- Education: %d/20\n" +
                "- Skills: %d/30\n" +
                "- Projects: %d/10",
                totalScore, experienceScore, educationScore, skillsScore, projectsScore
            );
        }
        public String getAssessment(int totalScore) {
            if (totalScore >= 90) {
                return "Excellent match. Candidate has exceptional qualifications.";
            } else if (totalScore >= 75) {
                return "Strong match. Candidate has strong qualifications.";
            } else if (totalScore >= 60) {
                return "Good match. Candidate meets most requirements.";
            } else if (totalScore >= 45) {
                return "Moderate match. Candidate meets some requirements.";
            } else {
                return "Weak match. Candidate does not meet many requirements.";
            }
        }  
        @GetMapping("/score")
        public ResponseEntity<UserScore> returnResult(){
            String message = getAssessment(totalScore);
            UserScore userResult = new UserScore(message,totalScore);
            return ResponseEntity.ok(userResult);
            
        }
    }
}