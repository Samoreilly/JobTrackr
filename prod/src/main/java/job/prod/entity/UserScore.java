/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.entity;

/**
 *
 * @author samor
 */
public class UserScore {

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getExperienceScore() {
        return experienceScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setExperienceScore(int experienceScore) {
        this.experienceScore = experienceScore;
    }

    public int getEducationScore() {
        return educationScore;
    }

    public void setEducationScore(int educationScore) {
        this.educationScore = educationScore;
    }

    public int getSkillsScore() {
        return skillsScore;
    }

    public void setSkillsScore(int skillsScore) {
        this.skillsScore = skillsScore;
    }

    public int getProjectsScore() {
        return projectsScore;
    }

    public void setProjectsScore(int projectsScore) {
        this.projectsScore = projectsScore;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }
    
    private int totalScore;
    private int experienceScore;
    private int educationScore;
    private int skillsScore;
    private int projectsScore;
    private String assessment;
    private int score;
    
    // Constructor, getters, and setters
    public UserScore(int totalScore, int experienceScore, int educationScore,
                   int skillsScore, int projectsScore, String assessment,int score) {
        this.totalScore = totalScore;
        this.experienceScore = experienceScore;
        this.educationScore = educationScore;
        this.skillsScore = skillsScore;
        this.projectsScore = projectsScore;
        this.assessment = assessment;
        this.score = score;
    }
    
    
}
