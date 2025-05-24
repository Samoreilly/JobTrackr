package job.prod.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long>{
    
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    String findUsernameById(Long id);
    Optional <User> findByEmail(String email);
    Optional<User> findByUsernameIgnoreCase(String username);
    
}
