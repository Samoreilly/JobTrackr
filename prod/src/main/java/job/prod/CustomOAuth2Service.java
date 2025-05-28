/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package job.prod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import job.prod.repo.Applications;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    
    @Autowired
    private UserRepo userRepo;

    @Override
   public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
       System.out.println("********** CustomOAuth2Service.loadUser CALLED **********");

       OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
       OAuth2User oAuth2User = delegate.loadUser(userRequest);

       System.out.println("All OAuth2 Attributes:");
       oAuth2User.getAttributes().forEach((key, value) -> 
           System.out.println(key + ": " + value));

       String registrationId = userRequest.getClientRegistration().getRegistrationId();
       System.out.println("Registration ID: " + registrationId);

       String email = oAuth2User.getAttribute("email");
       String name = oAuth2User.getAttribute("name");

       System.out.println("Extracted email: " + email);
       System.out.println("Extracted name: " + name);

       if (email != null) {

            User user = userRepo.findByEmail(email).orElseGet(() -> {
                System.out.println("Creating new user with email: " + email);
                String baseUsername = name != null ? name : email.split("@")[0];
                String username = baseUsername;

                int count = 0;
                // Try to find unique username by appending numbers
                while (userRepo.existsByUsername(username)) {
                    count++;
                    username = baseUsername + count;
                }

                User newUser = new User();
                newUser.setEmail(email);
                newUser.setUsername(username);
                newUser.setPassword("OAUTH2_USER");
                newUser.setMember(false);
                newUser.setHasPaid(false);


                User savedUser = userRepo.save(newUser);

                return savedUser;
            });
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attr != null) {
                HttpServletRequest request = attr.getRequest();
                HttpSession session = request.getSession(true);
                session.setAttribute("id", user.getId());
                System.out.println("Stored userId in session: " + user.getId());
            }

           
           System.out.println("User in database: " + user);
       } else {
           System.out.println("Email is null - cannot create user");
       }

       return new DefaultOAuth2User(
           Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
           oAuth2User.getAttributes(),
           "email"
       );
   }
   }


