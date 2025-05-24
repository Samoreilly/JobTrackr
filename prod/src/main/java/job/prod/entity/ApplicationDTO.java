/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.entity;

import java.time.LocalDateTime;

public class ApplicationDTO {
    
    public String job;
    public String position;
    public String status;
    public String description;
    public LocalDateTime date;
    public LocalDateTime interviewDate;
    
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(LocalDateTime interviewDate) {
        this.interviewDate = interviewDate;
    }
    
    public ApplicationDTO(String job, String position, String status, String description, LocalDateTime date, LocalDateTime interviewDate) {
        this.job = job;
        this.position = position;
        this.status = status;
        this.description = description;
        this.date = date;
        this.interviewDate = interviewDate;
    }
    
    
    
}
