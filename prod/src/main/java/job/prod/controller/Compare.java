package job.prod.controller;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import job.prod.entity.ComparisonResult;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class Compare {
    
    private StanfordCoreNLP pipeline;
    private String jobD;
    Set<String> imp = new HashSet<>();

    @PostConstruct
    public void init() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
        pipeline = new StanfordCoreNLP(props);
    }
    
    public String parseResume(String jobDesc, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename().toLowerCase();
        String text;

        if (fileName.endsWith(".pdf")) {
            text = parsePDF(file);
        } else if (fileName.endsWith(".docx")) {
            text = parseDocx(file);
        } else {
            throw new IllegalArgumentException("Unsupported file type. Only PDF and DOCX files are supported.");
        }

        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        StringBuilder result = new StringBuilder();
        for(CoreLabel token : document.tokens()){
            String word = token.word();
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            String ner = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
            String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);

            result.append(String.format("Word: %-15s POS: %-6s NER: %-12s Lemma: %-15s\n", word, pos, ner, lemma));
        }

        jobD = jobDesc;
        System.out.println(result.toString());
        return result.toString();
    }

    public ComparisonResult extractKeywords(String text) {
        CoreDocument document = new CoreDocument(text);
        pipeline.annotate(document);

        Set<String> stopwords = Set.of("the", "and", "is", "a", "an", "of", "to", "in", "for", "on", "with");
        List<String> keywords = new ArrayList<>();

        for (CoreLabel token : document.tokens()) {
            String lemma = token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase();
            String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);

            if ((pos.startsWith("NN") || pos.startsWith("VB") || pos.startsWith("JJ") || pos.startsWith("RB"))
                && lemma.length() > 2 
                && !stopwords.contains(lemma)) {
                keywords.add(lemma);
            }
        }

        return compareJobDesc(keywords);
    }    

    public ComparisonResult compareJobDesc(List<String> resumeKeywords) {
        if (resumeKeywords == null || resumeKeywords.isEmpty()) return new ComparisonResult(0, Set.of(),Set.of());

        System.out.println("Resume Keywords: " + resumeKeywords);

        Set<String> processedResumeKeywords = resumeKeywords.stream()
            .map(String::toLowerCase)
            .filter(kw -> kw.length() > 2)
            .collect(Collectors.toSet());

        Set<String> stopwords = Set.of("the", "and", "is", "a", "an", "of", "to", "in", "for", "on", "with");

        double totalScore = 0;
        int validJobsProcessed = 0;

        CoreDocument document = new CoreDocument(jobD);
        pipeline.annotate(document);

        Set<String> jobKeywords = document.tokens().stream()
            .filter(token -> {
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase();
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                return (pos.startsWith("NN") || pos.startsWith("VB") || 
                        pos.startsWith("JJ") || pos.startsWith("RB")) &&
                        lemma.length() > 2 && 
                        !stopwords.contains(lemma);
            })
            .map(token -> token.get(CoreAnnotations.LemmaAnnotation.class).toLowerCase())
            .collect(Collectors.toSet());
        

        Set<String> missedKeywords = new HashSet<>(jobKeywords);
        Set<String> matchedKeywords = new HashSet<>();
        for (String resumeKeyword : processedResumeKeywords) {
            if (jobKeywords.contains(resumeKeyword)) {
                matchedKeywords.add(resumeKeyword);
                missedKeywords.remove(resumeKeyword);
            } else {
                for (String jobKeyword : jobKeywords) {
                    if (jobKeyword.contains(resumeKeyword) || resumeKeyword.contains(jobKeyword)) {
                        if (jobKeyword.length() > 3 && resumeKeyword.length() > 3) {
                            matchedKeywords.add(resumeKeyword);

                            missedKeywords.remove(jobKeyword);
                            break;
                        }
                    }
                }
            }
        }
 
        imp = missedKeywords;

        double matchResume = processedResumeKeywords.isEmpty() ? 0 : 
                                (double) matchedKeywords.size() / processedResumeKeywords.size();
        double matchJob = (double) matchedKeywords.size() / jobKeywords.size();

        double harmonicMean = 0;
        if (matchResume > 0 || matchJob > 0) {
            harmonicMean = 2 * (matchResume * matchJob + 0.05) / (matchResume + matchJob + 0.1);
        }

        double score = harmonicMean * 100;
        totalScore += score;
        validJobsProcessed++;

        int finalScore = validJobsProcessed == 0 ? 0 : 
                        (int) Math.round((totalScore / validJobsProcessed) * 2.0);
        finalScore = Math.min(100, finalScore);

        System.out.println("Final score: " + finalScore);
        System.out.println("----------------------------------------"+ imp.toString());

        return new ComparisonResult(finalScore, imp,matchedKeywords);
    }

    public String parsePDF(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper stripper = new PDFTextStripper() {
                @Override
                protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                    text = text.replace("\u0000", "");
                    super.writeString(text, textPositions);
                }
            };

            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(document.getNumberOfPages());
            stripper.setAddMoreFormatting(true);
            stripper.setSpacingTolerance(0.5f);

            try {
                String extractedText = stripper.getText(document);
                if (extractedText == null || extractedText.trim().isEmpty()) {
                    stripper.setLineSeparator("\n");
                    stripper.setWordSeparator(" ");
                    extractedText = stripper.getText(document);
                }
                if (extractedText != null) {
                    extractedText = extractedText.replaceAll("\\p{C}", " ")
                                              .replaceAll("\\u0000", "")
                                              .replaceAll("[^\\p{Print}\\p{Space}]", " ");

                    extractedText = extractedText.replaceAll("\\s+", " ")
                                              .replaceAll("(?m)^\\s+", "")
                                              .trim();

                    return extractedText;
                }
                throw new IOException("Failed to extract text from PDF");
            } catch (Exception e) {
                return extractTextFallback(document);
            }
        } catch (Exception e) {
            throw new IOException("Error processing PDF file: " + e.getMessage(), e);
        }
    }

    private String extractTextFallback(PDDocument document) throws IOException {
        StringBuilder text = new StringBuilder();

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

            for (XWPFParagraph para : document.getParagraphs()) {
                String paraText = para.getText().trim();
                if (!paraText.isEmpty()) {
                    text.append(paraText).append("\n");
                }
            }

            return text.toString();
        }
    }
}
