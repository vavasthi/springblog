package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.Constants;
import in.springframework.blog.tutorials.MyConstants;
import in.springframework.blog.tutorials.pojos.MyTopicMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class MyTopicMessageSenderEndpoint {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(MyConstants.ANNOTATION_ROLE_USER)
    public String createUser(@RequestBody MyTopicMessage message) {

        kafkaTemplate.send(Constants.MY_TOPIC, message.getMessage());
        return "Message sent successfully!.";
    }
}
