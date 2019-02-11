package in.springframework.blog.tutorials;

import in.springframework.blog.tutorials.other.repository.OtherRepository;
import in.springframework.blog.tutorials.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"in.springframework.blog.tutorials"})
public class TutorialsApplication {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private OtherRepository otherRepository;

	public static void main(String[] args) {
		SpringApplication.run(TutorialsApplication.class, args);
	}
}
