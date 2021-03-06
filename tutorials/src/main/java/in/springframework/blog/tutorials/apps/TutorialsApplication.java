package in.springframework.blog.tutorials.apps;

import in.springframework.blog.tutorials.listeners.UserCreatedEventListener;
import in.springframework.blog.tutorials.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@ComponentScan(basePackages = {"in.springframework.blog.tutorials"})
@EnableAutoConfiguration
@EnableResourceServer
public class TutorialsApplication {

	@Autowired
	private UserCreatedEventListener userCreatedEventListener;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TutorialsApplication.class, args);
	}

}
