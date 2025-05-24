/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package job.prod.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author samor
 */
public interface ApplicationsRepo  extends JpaRepository<Applications,Long> {
    
    
    List<Applications> findApplicationsByUser_Id(Long id);
    Applications findApplicationsById(Long id);
    
}
