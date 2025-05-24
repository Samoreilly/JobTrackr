package job.prod.entity;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import job.prod.controller.ResumeController;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class Resume {
    
int score = 0;
private static final List<String> JOB_TITLES = Arrays.asList(
    // Original job titles
    "developer", "engineer", "architect", "lead", "manager", "director", "software engineer", "software developer", 
    "full-stack developer", "backend developer", "frontend developer", "data scientist", "data engineer", "senior developer", 
    "junior developer", "product manager", "qa engineer", "test engineer", "ui/ux designer", "machine learning engineer",
    "cloud engineer", "sysadmin", "database administrator", "devops engineer", "technical lead", "software tester", "tech consultant",
    
    // Additional job titles
    "principal engineer", "staff engineer", "distinguished engineer", "fellow", "cto", "chief technology officer", 
    "chief information officer", "cio", "chief data officer", "cdo", "chief security officer", "cso", 
    "chief product officer", "cpo", "vp of engineering", "vp engineering", "vp of technology", "vp technology",
    "head of engineering", "head of development", "head of technology", "head of data", "head of ai", "head of product",
    "engineering manager", "development manager", "technology manager", "data manager", "ai manager", "product manager",
    "technical program manager", "tpm", "program manager", "project manager", "scrum master", "agile coach",
    "release manager", "build engineer", "reliability engineer", "site reliability engineer", "sre",
    "platform engineer", "infrastructure engineer", "network engineer", "systems engineer", "security engineer",
    "cybersecurity engineer", "information security engineer", "infosec engineer", "penetration tester", "pen tester",
    "ethical hacker", "security analyst", "security architect", "network architect", "solutions architect",
    "enterprise architect", "cloud architect", "data architect", "systems architect", "integration architect",
    "android developer", "ios developer", "mobile developer", "react native developer", "flutter developer",
    "web developer", "php developer", "ruby developer", "javascript developer", "java developer", "python developer",
    "c# developer", ".net developer", "node.js developer", "nodejs developer", "go developer", "golang developer", 
    "rust developer", "scala developer", "kotlin developer", "swift developer", "react developer", "angular developer",
    "vue developer", "typescript developer", "unity developer", "game developer", "blockchain developer",
    "smart contract developer", "solidity developer", "embedded developer", "embedded systems engineer",
    "firmware engineer", "hardware engineer", "fpga engineer", "asic designer", "chip designer",
    "verification engineer", "validation engineer", "quality assurance engineer", "qa analyst", "quality engineer",
    "test automation engineer", "manual tester", "qa lead", "testing lead", "automation architect",
    "performance engineer", "performance tester", "load test engineer", "database engineer", "data warehouse engineer",
    "etl developer", "etl engineer", "data integration specialist", "data modeler", "database designer",
    "sql developer", "nosql specialist", "bi developer", "business intelligence developer", "bi analyst",
    "data analyst", "business analyst", "analytics engineer", "reporting analyst", "tableau developer",
    "power bi developer", "looker developer", "visualization specialist", "data visualization engineer",
    "machine learning scientist", "ai researcher", "research scientist", "applied scientist", "nlp engineer",
    "nlp scientist", "computer vision engineer", "computer vision scientist", "deep learning engineer",
    "ai engineer", "machine learning ops engineer", "mlops engineer", "ml engineer", "ai product manager",
    "data product manager", "technical product manager", "product owner", "ux researcher", "user researcher",
    "ux designer", "ui designer", "interaction designer", "visual designer", "product designer",
    "frontend architect", "backend architect", "api designer", "api architect", "microservices architect",
    "devops architect", "devops specialist", "cloud specialist", "aws architect", "aws engineer",
    "azure architect", "azure engineer", "gcp architect", "gcp engineer", "multicloud specialist",
    "kubernetes engineer", "kubernetes administrator", "docker specialist", "terraform engineer",
    "infrastructure architect", "network administrator", "systems administrator", "linux administrator",
    "windows administrator", "cloud administrator", "it specialist", "support engineer", "technical support engineer",
    "support specialist", "customer success engineer", "implementation specialist", "implementation engineer",
    "solutions engineer", "sales engineer", "presales engineer", "developer advocate", "developer relations",
    "developer evangelist", "technical writer", "documentation specialist", "knowledge base manager",
    "software evangelist", "open source maintainer", "open source contributor", "community manager",
    "growth engineer", "growth hacker", "full stack engineer", "frontend engineer", "backend engineer",
    "middleware engineer", "api engineer", "integration engineer", "automation engineer", "robotics engineer",
    "controls engineer", "mechatronics engineer", "simulation engineer", "graphics engineer", "rendering engineer",
    "shader programmer", "game engine developer", "gameplay programmer", "tools programmer", "technical artist",
    "3d programmer", "audio programmer", "cryptography engineer", "quantum computing engineer", "quantum developer",
    "chief architect", "principal architect", "lead architect", "solution delivery manager", "delivery manager",
    "innovation manager", "r&d engineer", "research engineer", "technology consultant", "it consultant",
    "digital transformation consultant", "modernization specialist", "legacy systems specialist"
);

// Expand the list of educational backgrounds
private static final List<String> RELEVANT_DEGREES = Arrays.asList(
    // Original degrees
    "ph.d", "doctorate", "master", "mba", "ms", "m.s", "bachelor", "b.a", "b.s", "ba", "bs", "associate", 
    "diploma", "certificate", "postgraduate", "doctoral", "bachelor of science", "bachelor of arts", "master of science",
    
    // Additional degrees
    "master of arts", "m.a", "ma", "bachelor of engineering", "b.eng", "beng", "master of engineering", "m.eng", "meng",
    "bachelor of technology", "b.tech", "btech", "master of technology", "m.tech", "mtech", "bachelor of computing",
    "master of computing", "bachelor of information technology", "master of information technology", "bachelor of business",
    "master of business", "doctor of philosophy", "doctor of science", "doctor of engineering", "d.eng", "deng",
    "doctor of technology", "d.tech", "dtech", "doctor of information technology", "doctor of business administration", "dba",
    "master of business administration", "executive mba", "emba", "master of computer science", "master of data science",
    "master of artificial intelligence", "master of machine learning", "master of cybersecurity", "master of information security",
    "master of software engineering", "master of computer engineering", "master of information systems", "master of applied science",
    "m.a.sc", "masc", "master of computational science", "master of computational engineering", "bachelor of applied science",
    "b.a.sc", "basc", "bachelor of computer science", "master of statistics", "bachelor of statistics", "master of mathematics",
    "bachelor of mathematics", "bachelor of software engineering", "bachelor of computer engineering", "bachelor of data science",
    "bachelor of artificial intelligence", "bachelor of cybersecurity", "bachelor of information systems",
    "graduate certificate", "professional certificate", "post-graduate diploma", "pgd", "post-graduate certificate", "pgc",
    "advanced diploma", "higher national diploma", "hnd", "higher national certificate", "hnc", "national diploma",
    "national certificate", "technical diploma", "vocational diploma", "vocational certificate", "associate of science",
    "associate of arts", "a.a", "a.s", "aas", "associate of applied science", "foundation degree", "fd",
    "bachelor of computing science", "bachelor of information science", "bachelor of computational science",
    "master of information science", "master of computer applications", "mca", "bachelor of computer applications", "bca",
    "bachelor of it", "bit", "master of it", "mit", "master of professional studies", "mps", "doctor of computing",
    "doctor of computer science", "doctor of information science", "licentiate", "magister", "habilitation",
    "bachelor of business informatics", "master of business informatics", "bachelor of business computing",
    "master of business computing", "integrated master", "msci", "meng", "mmath", "mphys",
    "master of philosophy", "mphil", "master of research", "mres", "doctor of professional studies", "dprof",
    "doctor of engineering practice", "bachelor with honors", "honors degree", "honours degree", "master's degree",
    "doctoral degree", "undergraduate degree", "graduate degree", "terminal degree", "professional doctorate"
);

// Expand the relevant fields of study list
private static final List<String> RELEVANT_FIELDS = Arrays.asList(
    // Original fields
    "computer science", "information technology", "software engineering", "data science", "electrical engineering", 
    "computer engineering", "mathematics", "statistics", "information systems", "artificial intelligence", "data analysis", 
    "robotics", "quantitative finance", "applied mathematics", "software design", "business analytics", "network engineering",
    
    // Additional fields
    "machine learning", "deep learning", "computational linguistics", "natural language processing", "nlp", 
    "computer vision", "human-computer interaction", "hci", "cybersecurity", "information security", 
    "network security", "cryptography", "blockchain technology", "distributed systems", "cloud computing", 
    "edge computing", "internet of things", "iot", "embedded systems", "real-time systems", "operating systems", 
    "systems programming", "compiler design", "programming language theory", "formal methods", 
    "algorithm design", "computational complexity", "parallel computing", "high-performance computing", "hpc", 
    "quantum computing", "quantum information science", "computational physics", "computational chemistry", 
    "computational biology", "bioinformatics", "computational neuroscience", "cognitive science", 
    "cognitive computing", "autonomous systems", "web science", "information retrieval", "database systems", 
    "data management", "big data", "data mining", "web development", "mobile development", "game development", 
    "computer graphics", "animation", "digital media", "multimedia systems", "digital signal processing", "dsp", 
    "control systems", "automation", "mechatronics", "electronic engineering", "telecommunications", 
    "wireless communications", "computer networks", "network architecture", "network protocols", 
    "systems architecture", "software architecture", "software testing", "quality assurance", 
    "software quality", "software verification", "software validation", "requirements engineering", 
    "software project management", "agile methodologies", "devops", "site reliability engineering", "sre", 
    "information theory", "computer algebra", "numerical analysis", "operational research", "operations research", 
    "optimization", "mathematical modeling", "simulation", "computational mathematics", "computational statistics", 
    "statistical learning", "bayesian statistics", "experimental design", "decision science", "data engineering", 
    "information engineering", "knowledge engineering", "knowledge representation", "semantic web", 
    "business intelligence", "business information systems", "management information systems", "mis", 
    "enterprise systems", "it management", "it strategy", "digital strategy", "digital transformation", 
    "fintech", "financial computing", "computational finance", "actuarial science", "risk analysis", 
    "econometrics", "mathematical economics", "computational economics", "web science", "digital humanities", 
    "technology ethics", "digital ethics", "ai ethics", "cyber law", "it law", "technology policy", 
    "information policy", "tech innovation", "r&d", "applied computing", "gaming technology", "virtual reality", "vr", 
    "augmented reality", "ar", "mixed reality", "computer simulations", "computational design", 
    "digital design", "interaction design", "user experience design", "user interface design", "ux/ui design", 
    "information architecture", "systems engineering", "software systems", "enterprise architecture", 
    "solution architecture", "tech architecture", "security engineering", "security architecture", 
    "multimedia engineering", "media informatics", "web engineering", "mobile computing", "pervasive computing", 
    "ubiquitous computing", "computational thinking", "computer ethics", "technology ethics", 
    "computational social science", "computational journalism", "logic programming", "functional programming", 
    "object-oriented design", "system administration", "technical support", "tech communication", 
    "scientific computing", "computational science", "engineering mathematics", "engineering statistics", 
    "mathematical finance", "applied computing", "computing science", "computer systems", "information science", 
    "digital systems", "intelligent systems", "expert systems", "knowledge-based systems", "decision support systems", 
    "business computing", "biomedical computing", "health informatics", "medical informatics", "clinical informatics", 
    "computational social science", "computational linguistics", "computational finance", "digital innovation", 
    "digital business", "digital marketing", "e-commerce technology", "enterprise software development", 
    "cloud architecture", "software product management", "product development", "product design", 
    "technology innovation", "innovation management", "computational engineering", "technology management", 
    "systems analysis", "web services", "service-oriented architecture", "microservices architecture"
);

// Expand the prestigious institution list
private static final List<String> PRESTIGIOUS_SCHOOLS = Arrays.asList(
    // Original schools
    "mit", "stanford", "harvard", "berkeley", "carnegie mellon", "princeton", "oxford", "cambridge", "caltech", 
    "eth zurich", "university of chicago", "columbia", "yale", "cornell", "uc berkeley", "university of oxford",
    "university of cambridge", "imperial college london", "university of toronto", "university of michigan", "university of california",
    
    // Additional schools
    "university of california los angeles", "ucla", "university of california san diego", "ucsd", 
    "university of california santa barbara", "ucsb", "university of california irvine", "uci", 
    "university of california davis", "ucd", "university of washington", "university of illinois", 
    "university of illinois urbana-champaign", "uiuc", "university of illinois at urbana-champaign", 
    "georgia tech", "georgia institute of technology", "university of texas", "ut austin", 
    "university of texas at austin", "university of wisconsin", "university of wisconsin-madison", 
    "purdue university", "university of pennsylvania", "upenn", "penn", "dartmouth college", 
    "brown university", "northwestern university", "nyu", "new york university", 
    "university of southern california", "usc", "duke university", "johns hopkins university", 
    "university of maryland", "university of maryland college park", "umcp", "rice university", 
    "vanderbilt university", "university of north carolina", "unc chapel hill", 
    "university of north carolina at chapel hill", "university of minnesota", "uminn", 
    "university of massachusetts", "umass", "university of massachusetts amherst", 
    "university of colorado", "university of colorado boulder", "cu boulder", 
    "rutgers university", "penn state university", "pennsylvania state university", 
    "university of virginia", "uva", "university of florida", "uf", "university of arizona", 
    "arizona state university", "asu", "university of utah", "ohio state university", 
    "tsinghua university", "peking university", "university of tokyo", "tokyo university", "todai", 
    "seoul national university", "snu", "kaist", "korea advanced institute of science and technology", 
    "national university of singapore", "nus", "nanyang technological university", "ntu singapore", 
    "university of melbourne", "university of sydney", "university of new south wales", "unsw", 
    "australian national university", "anu", "university of queensland", "uq australia", 
    "mcgill university", "university of british columbia", "ubc", "university of waterloo", 
    "university of alberta", "university of montreal", "université de montréal", "udem", 
    "technion", "israel institute of technology", "hebrew university of jerusalem", 
    "tel aviv university", "weizmann institute of science", "swiss federal institute of technology", 
    "epfl", "école polytechnique fédérale de lausanne", "technical university of munich", "tum", 
    "tu munich", "rwth aachen", "rwth aachen university", "heidelberg university", 
    "university of heidelberg", "humboldt university of berlin", "humboldt-universität zu berlin", 
    "kth royal institute of technology", "delft university of technology", "tu delft", 
    "eindhoven university of technology", "tu eindhoven", "wageningen university", 
    "ghent university", "ku leuven", "catholic university of leuven", "sapienza university of rome", 
    "sapienza university", "university of rome", "polytechnic university of milan", "politecnico di milano", 
    "university of bologna", "university of manchester", "university of edinburgh", "university of warwick", 
    "university college london", "ucl", "london school of economics", "lse", "king's college london", 
    "university of bristol", "university of nottingham", "university of sheffield", "university of leeds", 
    "university of southampton", "trinity college dublin", "university of dublin", "university of copenhagen", 
    "copenhagen university", "university of amsterdam", "university of helsinki", "aalto university", 
    "university of oslo", "oslo university", "university of stockholm", "stockholm university", 
    "university of barcelona", "universidad de barcelona", "autonomous university of madrid", 
    "universidad autónoma de madrid", "complutense university of madrid", "universidad complutense de madrid", 
    "university of vienna", "universität wien", "vienna university", "university of geneva", 
    "université de genève", "university of zurich", "universität zürich", "moscow state university", 
    "lomonosov moscow state university", "saint petersburg state university", "university of hong kong", "hku", 
    "chinese university of hong kong", "cuhk", "hong kong university of science and technology", "hkust", 
    "ecole polytechnique", "école polytechnique", "sorbonne university", "université sorbonne", 
    "paris sciences et lettres", "psl university", "psl", "indian institute of technology", "iit", 
    "iit delhi", "iit bombay", "iit madras", "iit kanpur", "iit kharagpur", "indian institute of science", 
    "iisc", "university of tokyo", "tokyo university", "kyoto university", "osaka university", 
    "tohoku university", "nagoya university", "hokkaido university", "chalmers university of technology", 
    "lund university", "uppsala university", "aarhus university", "university of bergen", "norwegian university of science and technology", 
    "ntnu", "university of helsinki", "university of lisbon", "universidade de lisboa", "university of porto", 
    "universidade do porto", "university of são paulo", "universidade de são paulo", "usp", 
    "state university of campinas", "unicamp", "federal university of rio de janeiro", "universidade federal do rio de janeiro", 
    "ufrj", "university of buenos aires", "universidad de buenos aires", "uba", "national autonomous university of mexico", 
    "universidad nacional autónoma de méxico", "unam", "university of cape town", "uct", "university of the witwatersrand", 
    "wits university", "cairo university", "american university in cairo", "auc", "university of jordan", 
    "american university of beirut", "aub", "university of tehran", "sharif university of technology", 
    "king fahd university of petroleum and minerals", "kfupm", "king abdullah university of science and technology", 
    "kaust", "indian statistical institute", "isi", "african institute for mathematical sciences", "aims", 
    "university of nairobi", "makerere university", "university of ghana", "university of namibia", 
    "university of ibadan", "addis ababa university", "university of dar es salaam", "university of mauritius", 
    "istituto superiore di sanità", "university of padua", "university of pisa", "university of florence", 
    "university of groningen", "vrije universiteit amsterdam", "leiden university", "university of freiburg", 
    "goethe university frankfurt", "ludwig maximilian university of munich", "lmu munich", "university of bonn", 
    "charles university", "university of warsaw", "warsaw university", "jagiellonian university", 
    "university of bucharest", "babeș-bolyai university", "polytechnic university of bucharest", 
    "saint petersburg university", "national research university higher school of economics", "hse university", 
    "moscow institute of physics and technology", "mipt", "novosibirsk state university", "zhejiang university", 
    "fudan university", "shanghai jiao tong university", "university of science and technology of china", 
    "harbin institute of technology", "nanjing university", "wuhan university", "sun yat-sen university", 
    "korea university", "yonsei university", "pohang university of science and technology", "postech", 
    "sungkyunkwan university", "hanyang university", "kyung hee university", "tokyo institute of technology", 
    "waseda university", "keio university", "university of tsukuba", "uc davis", "uc irvine", "uc santa barbara", 
    "uc san diego", "uc riverside", "university of california riverside", "ucr", 
    "university of southern california", "usc", "illinois institute of technology", "iit chicago", 
    "rensselaer polytechnic institute", "rpi", "stevens institute of technology", 
    "worcester polytechnic institute", "wpi", "case western reserve university", 
    "university of notre dame", "lehigh university", "northeastern university", 
    "university of pittsburgh", "university of rochester", "boston university", "bu", 
    "stony brook university", "suny stony brook", "university at buffalo", "suny buffalo", 
    "university of connecticut", "uconn", "university of california santa cruz", "ucsc", "university of oregon", 
    "university of tennessee", "university of oklahoma", "university of iowa", "florida state university", "fsu", 
    "texas a&m university", "tamu", "university of georgia", "university of south carolina"
);
    private static final Map<String, List<String>> SECTION_HEADERS = new LinkedHashMap<>();
    static {
        SECTION_HEADERS.put("experience", Arrays.asList(
            "experience", "work history", "professional experience", "employment history",
            "work experience", "career history", "industrial placement", "internship"
        ));
        SECTION_HEADERS.put("education", Arrays.asList(
            "education", "academic background", "qualifications", "studies",
            "university", "school", "college", "bsc", "degree"
        ));
        SECTION_HEADERS.put("skills", Arrays.asList(
            "skills", "technical skills", "core competencies", "proficiencies",
            "programming", "databases", "miscellaneous", "languages", "frameworks"
        ));
        SECTION_HEADERS.put("projects", Arrays.asList(
            "projects", "hackathons", "personal projects", "technical projects",
            "university challenge", "android application", "findmyglasses"
        ));
    }

    // Updated skill keywords to include all from Alison's resume
    private static final Map<String, List<String>> SKILL_KEYWORDS = new HashMap<>();
    static {
        SKILL_KEYWORDS.put("programming", Arrays.asList(
            "c", "c++", "c#", "go", "java", "javascript", "php", "python"
        ));
        SKILL_KEYWORDS.put("databases", Arrays.asList(
            "couchdb", "interbase", "mongodb", "mysql", "sql server"
        ));
        SKILL_KEYWORDS.put("miscellaneous", Arrays.asList(
            "windows", "mac os", "linux", "docker", "git", "kubernetes",
            "aws", "google cloud", "bash", "microsoft office", "access",
            "excel", "powerpoint", "word"
        ));
    }

    public UserScore scoreResume(MultipartFile file) throws IOException {
        String resumeText = parseResume(file);
        Map<String, String> sections = extractSections(resumeText);
        return calculateScore(sections);
    }

    public String parseResume(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toLowerCase();
        String text;

        if (fileName.endsWith(".pdf")) {
            text = parsePDF(file);
        } else if (fileName.endsWith(".docx")) {
            text = parseDocx(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type. Only PDF and DOCX files are supported.");
        }

        return text;
    }

    public String parsePDF(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {
            
            // Create PDF stripper with specific configuration
            PDFTextStripper stripper = new PDFTextStripper() {
                @Override
                protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                    // Remove any null characters that might cause issues
                    text = text.replace("\u0000", "");
                    super.writeString(text, textPositions);
                  
                }
            };
            
            // Configure the stripper
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(document.getNumberOfPages());
            stripper.setAddMoreFormatting(true);
            stripper.setSpacingTolerance(0.5f);
            
            // Extract text with enhanced error handling
            try {
                String extractedText = stripper.getText(document);
                
                // Handle potential encoding issues
                if (extractedText == null || extractedText.trim().isEmpty()) {
                    // Try alternative encoding
                    stripper.setLineSeparator("\n");
                    stripper.setWordSeparator(" ");
                    extractedText = stripper.getText(document);
                }
                
                // Post-process the extracted text
                if (extractedText != null) {
                    // Remove any null characters or invalid Unicode
                    extractedText = extractedText.replaceAll("\\p{C}", " ")
                                              .replaceAll("\\u0000", "")
                                              .replaceAll("[^\\p{Print}\\p{Space}]", " ");
                    
                    // Normalize whitespace
                    extractedText = extractedText.replaceAll("\\s+", " ")
                                              .replaceAll("(?m)^\\s+", "")
                                              .trim();
                    
                    return extractedText;
                }
                
                throw new IOException("Failed to extract text from PDF");
            } catch (Exception e) {
                // If standard extraction fails, try fallback method
                return extractTextFallback(document);
            }
        } catch (Exception e) {
            throw new IOException("Error processing PDF file: " + e.getMessage(), e);
        }
    }

    private String extractTextFallback(PDDocument document) throws IOException {
        StringBuilder text = new StringBuilder();
        
        // Try to extract text page by page
        for (int i = 0; i < document.getNumberOfPages(); i++) {
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setStartPage(i + 1);
            stripper.setEndPage(i + 1);
            
            try {
                String pageText = stripper.getText(document);
                if (pageText != null && !pageText.trim().isEmpty()) {
                    text.append(pageText).append("\n");
                }
            } catch (Exception e) {
                // Log the error but continue with next page
                System.err.println("Error extracting text from page " + (i + 1) + ": " + e.getMessage());
            }
        }
        
        String result = text.toString().trim();
        if (result.isEmpty()) {
            throw new IOException("Could not extract any text from the PDF file");
        }
        
        return result;
    }

    private String parseDocx(MultipartFile file) throws IOException {
        try (XWPFDocument document = new XWPFDocument(file.getInputStream())) {
            StringBuilder text = new StringBuilder();
            
            // Process paragraphs
            for (XWPFParagraph para : document.getParagraphs()) {
                String paraText = para.getText().trim();
                if (!paraText.isEmpty()) {
                    text.append(paraText).append("\n");
                }
            }
            
            return text.toString();
        }
    }

    private String preprocessText(String text) {
        // Remove multiple spaces
        text = text.replaceAll("\\s+", " ");
        
        // Normalize line endings
        text = text.replaceAll("\\r\\n|\\r", "\n");
        
        // Remove special characters while preserving important punctuation
        text = text.replaceAll("[^\\w\\s.,;:@()\\-–—&%/]", " ");
        
        // Normalize dashes
        text = text.replaceAll("[\\-–—]", "-");
        
        // Fix common date format issues
        text = text.replaceAll("(?i)(jan|january)\\s*[.,]?\\s*(\\d{4})", "January $2");
        text = text.replaceAll("(?i)(feb|february)\\s*[.,]?\\s*(\\d{4})", "February $2");
        text = text.replaceAll("(?i)(mar|march)\\s*[.,]?\\s*(\\d{4})", "March $2");
        text = text.replaceAll("(?i)(apr|april)\\s*[.,]?\\s*(\\d{4})", "April $2");
        text = text.replaceAll("(?i)(may)\\s*[.,]?\\s*(\\d{4})", "May $2");
        text = text.replaceAll("(?i)(jun|june)\\s*[.,]?\\s*(\\d{4})", "June $2");
        text = text.replaceAll("(?i)(jul|july)\\s*[.,]?\\s*(\\d{4})", "July $2");
        text = text.replaceAll("(?i)(aug|august)\\s*[.,]?\\s*(\\d{4})", "August $2");
        text = text.replaceAll("(?i)(sep|sept|september)\\s*[.,]?\\s*(\\d{4})", "September $2");
        text = text.replaceAll("(?i)(oct|october)\\s*[.,]?\\s*(\\d{4})", "October $2");
        text = text.replaceAll("(?i)(nov|november)\\s*[.,]?\\s*(\\d{4})", "November $2");
        text = text.replaceAll("(?i)(dec|december)\\s*[.,]?\\s*(\\d{4})", "December $2");
        
        // Normalize section headers
        text = text.replaceAll("(?i)\\b(work\\s+experience|employment\\s+history|professional\\s+experience)\\b", "Experience");
        text = text.replaceAll("(?i)\\b(education|academic\\s+background|qualifications)\\b", "Education");
        text = text.replaceAll("(?i)\\b(skills|technical\\s+skills|core\\s+competencies)\\b", "Skills");
        text = text.replaceAll("(?i)\\b(projects|personal\\s+projects|technical\\s+projects)\\b", "Projects");
        
        // Remove extra newlines while preserving section breaks
        text = text.replaceAll("\\n{3,}", "\n\n");
        
        return text.trim();
        

    }

    private Map<String, String> extractSections(String resumeText) {
        Map<String, String> sections = new HashMap<>();
        List<SectionMatch> matches = new ArrayList<>();

        // Find all section headers with their positions
        for (Map.Entry<String, List<String>> entry : SECTION_HEADERS.entrySet()) {
            String sectionName = entry.getKey();
            for (String header : entry.getValue()) {
                Pattern pattern = Pattern.compile("(?i)\\b" + Pattern.quote(header) + "\\b");
                Matcher matcher = pattern.matcher(resumeText);
                while (matcher.find()) {
                    matches.add(new SectionMatch(sectionName, matcher.start(), header.length()));
                }
            }
        }

        // Sort matches by position
        Collections.sort(matches);

        // Extract sections
        for (int i = 0; i < matches.size(); i++) {
            SectionMatch current = matches.get(i);
            int startPos = current.position + current.headerLength;
            int endPos;
            
            // Determine end position
            if (i < matches.size() - 1) {
                endPos = matches.get(i + 1).position;
            } else {
                endPos = resumeText.length();
            }

            // Validate indices
            if (startPos > endPos || startPos >= resumeText.length() || endPos > resumeText.length()) {
                continue; // Skip this section if indices are invalid
            }

            // Extract and clean section content
            String sectionContent = resumeText.substring(startPos, endPos).trim();
            
            // Only add non-empty sections
            if (!sectionContent.isEmpty()) {
                // If section already exists, append new content
                if (sections.containsKey(current.sectionName)) {
                    String existingContent = sections.get(current.sectionName);
                    sectionContent = existingContent + "\n\n" + sectionContent;
                }
                sections.put(current.sectionName, sectionContent);
            }
        }

        return sections;
    }

    private static class SectionMatch implements Comparable<SectionMatch> {
        String sectionName;
        int position;
        int headerLength;

        SectionMatch(String sectionName, int position, int headerLength) {
            this.sectionName = sectionName;
            this.position = position;
            this.headerLength = headerLength;
        }

        @Override
        public int compareTo(SectionMatch other) {
            return Integer.compare(this.position, other.position);
        }
    }

    private UserScore calculateScore(Map<String, String> sections) {
        // Debug print sections
        System.out.println("\n=== Extracted Sections ===");
        sections.forEach((key, value) -> {
            String preview = (value != null && !value.isEmpty()) 
                ? value.substring(0, Math.min(100, value.length())) 
                : "[EMPTY]";
            System.out.printf("%-12s: %s...\n", key.toUpperCase(), preview);
        });

        // Calculate individual scores
        int experienceScore = sections.getOrDefault("experience", "").isEmpty() ? 
            0 : calculateExperienceScore(sections.get("experience"));
        
        int educationScore = sections.getOrDefault("education", "").isEmpty() ? 
            0 : calculateEducationScore(sections.get("education"));
        
        int skillsScore = sections.getOrDefault("skills", "").isEmpty() ? 
            0 : calculateSkillsScore(sections.get("skills"));
        
        int projectsScore = sections.getOrDefault("projects", "").isEmpty() ? 
            0 : calculateProjectsScore(sections.get("projects"));

        // Calculate total score
        int totalScore = (int) (experienceScore * 0.4 + educationScore * 0.2 +
                               skillsScore * 0.3 + Math.min(projectsScore, 10) * 0.1);

        return new UserScore(totalScore, experienceScore, educationScore,
                           skillsScore, projectsScore, generateAssessment(totalScore),score);
    }

    private int calculateExperienceScore(String content) {
        int score = 0;
        String[] jobs = content.split("(?m)^\\s*$"); // Split on blank lines

        for (String job : jobs) {
            if (job.trim().isEmpty()) continue;

            // Score for job title
            score += calculateJobTitleScore(job);
            
            // Score for duration
            score += calculateDurationScore(job);
            
            // Score for skills mentioned
            score += calculateJobSkillsScore(job);
        }

        return Math.min(score, 50);
    }

    private int calculateJobTitleScore(String job) {
        int score = 0;
        
        // Check for job titles from our predefined list
        for (String title : JOB_TITLES) {
            if (Pattern.compile("(?i)\\b" + Pattern.quote(title) + "\\b").matcher(job).find()) {
                score += 5;
                break; // Only count one title per job
            }
        }
        
        // Additional points for senior/lead positions
        if (job.toLowerCase().matches(".*(senior|lead|principal|staff|architect|head|chief|director|vp|manager).*")) {
            score += 3;
        }
        
        return score;
    }

    private int calculateDurationScore(String job) {
        int score = 0;
        
        // Pattern for various date formats
        List<Pattern> datePatterns = Arrays.asList(
            // Standard format: "Month Year - Month Year"
            Pattern.compile("(?i)(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)\\s*[,.]?\\s*(20\\d{2})\\s*(?:–|-|to)\\s*(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)\\s*[,.]?\\s*(20\\d{2})"),
            
            // Year range format: "2020-2022" or "2020 to 2022"
            Pattern.compile("(20\\d{2})\\s*(?:–|-|to)\\s*(20\\d{2})"),
            
            // Present format: "Month Year - Present"
            Pattern.compile("(?i)(jan(?:uary)?|feb(?:ruary)?|mar(?:ch)?|apr(?:il)?|may|jun(?:e)?|jul(?:y)?|aug(?:ust)?|sep(?:tember)?|oct(?:ober)?|nov(?:ember)?|dec(?:ember)?)\\s*[,.]?\\s*(20\\d{2})\\s*(?:–|-|to)\\s*(?:present|current|now)"),
            
            // Year to Present: "2020 - Present"
            Pattern.compile("(20\\d{2})\\s*(?:–|-|to)\\s*(?:present|current|now)")
        );

        for (Pattern pattern : datePatterns) {
            Matcher matcher = pattern.matcher(job);
            if (matcher.find()) {
                try {
                    int startYear;
                    int endYear;
                    
                    if (matcher.group().toLowerCase().contains("present")) {
                        startYear = Integer.parseInt(matcher.group().replaceAll("[^0-9]", ""));
                        endYear = Calendar.getInstance().get(Calendar.YEAR);
                    } else {
                        // Extract years from the match
                        String dateRange = matcher.group();
                        Pattern yearPattern = Pattern.compile("20\\d{2}");
                        Matcher yearMatcher = yearPattern.matcher(dateRange);
                        
                        if (yearMatcher.find()) {
                            startYear = Integer.parseInt(yearMatcher.group());
                            if (yearMatcher.find()) {
                                endYear = Integer.parseInt(yearMatcher.group());
                            } else {
                                continue; // Invalid date range
                            }
                        } else {
                            continue; // No years found
                        }
                    }
                    
                    int years = endYear - startYear;
                    score += Math.min(years * 3, 10); // Cap at 10 points per position
                    break; // Only use the first valid date range found
                } catch (Exception e) {
                    // Skip if date parsing fails
                    continue;
                }
            }
        }
        
        return score;
    }

    private int calculateJobSkillsScore(String job) {
        int score = 0;
        Set<String> foundSkills = new HashSet<>(); // To avoid counting duplicates
        
        // Check for technical skills
        for (List<String> keywords : SKILL_KEYWORDS.values()) {
            for (String keyword : keywords) {
                if (Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b", Pattern.CASE_INSENSITIVE).matcher(job).find()) {
                    if (foundSkills.add(keyword)) { // Only add score if skill wasn't counted before
                        score += 2;
                    }
                }
            }
        }
        
        // Additional points for technical achievements
        if (job.toLowerCase().matches(".*(developed|implemented|architected|designed|built|created|launched|optimized|improved).*")) {
            score += 2;
        }
        
        // Points for team leadership/management
        if (job.toLowerCase().matches(".*(team|managed|supervised|mentored|led|coordinated|directed).*")) {
            score += 2;
        }
        
        return score;
    }

    private int calculateEducationScore(String content) {
        int score = 0;
        String[] educationEntries = content.split("(?m)^\\s*$"); // Split on blank lines

        for (String entry : educationEntries) {
            if (entry.trim().isEmpty()) continue;

            // Score for degree level
            score += calculateDegreeScore(entry);
            
            // Score for institution prestige
            score += calculateInstitutionScore(entry);
            
            // Score for academic performance
            score += calculateGradeScore(entry);
            
            // Score for relevant field of study
            score += calculateFieldScore(entry);
        }

        return Math.min(score, 20);
    }

    private int calculateDegreeScore(String entry) {
        int score = 0;
        String lowerEntry = entry.toLowerCase();

        // Check for highest degree level first
        if (containsAny(lowerEntry, Arrays.asList("ph.d", "phd", "doctorate", "doctoral"))) {
            score += 10;
        } else if (containsAny(lowerEntry, Arrays.asList("master", "msc", "ms", "ma", "meng", "mba"))) {
            score += 8;
        } else if (containsAny(lowerEntry, Arrays.asList("bachelor", "bsc", "bs", "ba", "beng", "b.e"))) {
            score += 6;
        } else if (containsAny(lowerEntry, Arrays.asList("associate", "diploma", "certificate", "certification"))) {
            score += 4;
        }

        return score;
    }

    private int calculateInstitutionScore(String entry) {
        int score = 0;
        String lowerEntry = entry.toLowerCase();

        // Check for prestigious institutions
        for (String school : PRESTIGIOUS_SCHOOLS) {
            if (lowerEntry.contains(school.toLowerCase())) {
                score += 5;
                break;
            }
        }

        // Additional points for university/college mention
        if (Pattern.compile("\\b(university|college|institute|school)\\b", Pattern.CASE_INSENSITIVE).matcher(entry).find()) {
            score += 2;
        }

        return score;
    }

    private int calculateGradeScore(String entry) {
        int score = 0;
        String lowerEntry = entry.toLowerCase();

        // GPA scoring (various formats)
        Pattern gpaPattern = Pattern.compile("\\b(?:gpa|grade point average)?\\s*(?:of)?\\s*(\\d\\.\\d+)\\b", Pattern.CASE_INSENSITIVE);
        Matcher gpaMatcher = gpaPattern.matcher(entry);
        if (gpaMatcher.find()) {
            try {
                double gpa = Double.parseDouble(gpaMatcher.group(1));
                if (gpa >= 3.7) score += 5;
                else if (gpa >= 3.3) score += 4;
                else if (gpa >= 3.0) score += 3;
                else if (gpa >= 2.7) score += 2;
            } catch (NumberFormatException e) {
                // Ignore parsing errors
            }
        }

        // UK/International grade scoring
        if (containsAny(lowerEntry, Arrays.asList("first class", "1st class", "distinction"))) {
            score += 5;
        } else if (containsAny(lowerEntry, Arrays.asList("second class upper", "2:1", "upper second", "merit"))) {
            score += 4;
        } else if (containsAny(lowerEntry, Arrays.asList("second class lower", "2:2", "lower second", "pass"))) {
            score += 3;
        }

        // Honors and achievements
        if (containsAny(lowerEntry, Arrays.asList("summa cum laude", "highest honors", "highest distinction"))) {
            score += 5;
        } else if (containsAny(lowerEntry, Arrays.asList("magna cum laude", "high honors", "high distinction"))) {
            score += 4;
        } else if (containsAny(lowerEntry, Arrays.asList("cum laude", "honors", "distinction"))) {
            score += 3;
        }

        return score;
    }

    private int calculateFieldScore(String entry) {
        int score = 0;
        String lowerEntry = entry.toLowerCase();

        // Check for relevant fields of study
        for (String field : RELEVANT_FIELDS) {
            if (Pattern.compile("\\b" + Pattern.quote(field) + "\\b", Pattern.CASE_INSENSITIVE).matcher(entry).find()) {
                score += 3;
                break; // Only count one field per entry
            }
        }

        return score;
    }

    private boolean containsAny(String text, List<String> keywords) {
        for (String keyword : keywords) {
            if (Pattern.compile("\\b" + Pattern.quote(keyword) + "\\b", Pattern.CASE_INSENSITIVE).matcher(text).find()) {
                return true;
            }
        }
        return false;
    }

    private int calculateSkillsScore(String content) {
        int score = 0;
        for (List<String> keywords : SKILL_KEYWORDS.values()) {
            for (String keyword : keywords) {
                if (Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE)
                          .matcher(content).find()) {
                    score += 2;
                }
            }
        }
        return Math.min(score, 30);
    }

    private int calculateProjectsScore(String content) {
        int score = 0;
        
        // Project count
        String[] projects = content.split("\n\n");
        score += Math.min(projects.length * 5, 15);
        
        // Technical keywords
        for (List<String> keywords : SKILL_KEYWORDS.values()) {
            for (String keyword : keywords) {
                if (Pattern.compile("\\b" + keyword + "\\b", Pattern.CASE_INSENSITIVE)
                          .matcher(content).find()) {
                    score += 2;
                }
            }
        }
        
        // GitHub/demo indicators
        if (content.matches("(?i).*\\b(github|play store|download|published)\\b.*")) {
            score += 5;
        }

        return Math.min(score, 25);
    }

    private String generateAssessment(int totalScore) {
        if (totalScore >= 70) return "You're a standout candidate! Your technical expertise and experience make you a strong fit for top roles.";
        if (totalScore >= 50) return "You have a solid foundation! With some targeted improvements, you could be an excellent candidate.";
        if (totalScore >= 30) return "You're on the right track, but could benefit from more hands-on experience and skill development.";
        return "Consider focusing on building more technical experience and highlighting your achievements more effectively.";
    }
}