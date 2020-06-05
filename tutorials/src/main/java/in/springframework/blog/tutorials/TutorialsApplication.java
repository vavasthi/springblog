package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.other.repository.OtherRepository;
import in.springframework.blog.tutorials.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@ComponentScan(basePackages = {"in.springframework.blog.tutorials", "in.springframework.blog.tutorials.configs"})
@EnableAutoConfiguration
public class TutorialsApplication {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OtherRepository otherRepository;

	public static void main(String[] args) {
		SpringApplication.run(TutorialsApplication.class, args);
	}
}
