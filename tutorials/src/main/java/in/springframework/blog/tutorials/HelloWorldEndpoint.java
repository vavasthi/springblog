package in.springframework.blog.tutorials;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/hello")
public class HelloWorldEndpoint {

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Hello> getHello() {
        Hello h = new Hello();
        h.setMessage("Hello");
        h.setName("World");
        return Optional.of(h);
    }
}
