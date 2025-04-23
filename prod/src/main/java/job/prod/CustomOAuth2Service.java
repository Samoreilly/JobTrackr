/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod;

import java.util.Collections;
import java.util.Map;
import job.prod.repo.User;
import job.prod.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    @Autowired
    private UserRepo userRepo;

    @Override
   public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       System.out.println("********** CustomOAuth2Service.loadUser CALLED **********");

       OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
       OAuth2User oAuth2User = delegate.loadUser(userRequest);

       // Print all attributes for debugging
       System.out.println("All OAuth2 Attributes:");
       oAuth2User.getAttributes().forEach((key, value) -> 
           System.out.println(key + ": " + value));

       // Get registration ID (should be "google" in your case)
       String registrationId = userRequest.getClientRegistration().getRegistrationId();
       System.out.println("Registration ID: " + registrationId);

       String email = oAuth2User.getAttribute("email");
       String name = oAuth2User.getAttribute("name");

       System.out.println("Extracted email: " + email);
       System.out.println("Extracted name: " + name);

       if (email != null) {

           User user = userRepo.findByEmail(email).orElseGet(() -> {
               System.out.println("Creating new user with email: " + email);
               User newUser = new User();
               newUser.setEmail(email);
               newUser.setUsername(name != null ? name : email.split("@")[0]);
               newUser.setPassword("OAUTH2_USER"); // Set a placeholder
               return userRepo.save(newUser);
           });
           System.out.println("User in database: " + user);
       } else {
           System.out.println("Email is null - cannot create user");
       }

       return new DefaultOAuth2User(
           Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
           oAuth2User.getAttributes(),
           "email"  // Name attribute key
       );
   }
   }


