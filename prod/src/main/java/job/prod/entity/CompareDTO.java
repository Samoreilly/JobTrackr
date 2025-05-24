/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.entity;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author samor
 */
public class CompareDTO {
    
    private String jobDesc;
    private MultipartFile file;

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public CompareDTO(String jobDesc, MultipartFile file) {
        this.jobDesc = jobDesc;
        this.file = file;
    }
    
}
