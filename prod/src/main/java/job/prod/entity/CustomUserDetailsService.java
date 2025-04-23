//
//package job.prod.entity;
//
//
//
//import java.util.Collections;
//import job.prod.repo.User;
//import job.prod.repo.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
// 
//    @Autowired
//    private UserRepo userRepo;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        
//        User user = userRepo.findByUsernameIgnoreCase(username);
//        
//        if (user == null) {
//            throw new UsernameNotFoundException("user not found");
//        }
//
//        return new org.springframework.security.core.userdetails.User(//gets details from database and stores it to be compared later
//            user.getUsername(),
//            user.getPassword(),
//            Collections.emptyList()
//        );
//    }
//}
//
