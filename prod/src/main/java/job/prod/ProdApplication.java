package job.prod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})//removes login for spring security
public class ProdApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdApplication.class, args);
	}

}
