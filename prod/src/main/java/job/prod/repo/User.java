/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod.repo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author samor
 */
@Entity
public class User{
    
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false, unique = true)
   private String username;
   
   @Column(unique = false)
   private String email;
   
   @Column(nullable = true)
   private String password;

    public Long getId() {
        return id;
    }

    public void setUserid(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public User(){
        
    }
    
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
   
   
    
}
