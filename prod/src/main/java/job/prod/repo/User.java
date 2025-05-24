package job.prod.repo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class User {
    
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false, unique = true)
   private String username;
   
   @Column(unique = true)
   private String email;
   
   @Column(nullable = true)
   private String password;
   
   @Column(columnDefinition = "BOOLEAN DEFAULT false")
   private boolean member;

   public boolean isMember() {
        return member;
   }

   public void setMember(boolean member) {
        this.member = member;
   }
   public Long getId() {
       return id;
   }

   public void setId(Long id) {
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

  
   public User() {}

   public User(String username, String email, String password,boolean member) {
       this.username = username;
       this.email = email;
       this.password = password;
       this.member = member;
   }
}
