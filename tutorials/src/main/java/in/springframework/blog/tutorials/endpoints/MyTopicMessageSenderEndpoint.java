package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.Constants;
import in.springframework.blog.tutorials.MyConstants;
import in.springframework.blog.tutorials.pojos.MyTopicMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class MyTopicMessageSenderEndpoint {

    @Autowired
    @Qualifier(Constants.FIRST_TOPIC_TEMPLATE_NAME)
    private KafkaTemplate firstKafkaTemplate;

    @Autowired
    @Qualifier(Constants.SECOND_TOPIC_TEMPLATE_NAME)
    private KafkaTemplate secondKafkaTemplate;

    Logger logger = Logger.getLogger(MyTopicMessageSenderEndpoint.class);

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(MyConstants.ANNOTATION_ROLE_USER)
    public String createUser(@RequestBody MyTopicMessage message) {

        switch(message.getTopicName()) {
            case FirstTopic:
                firstKafkaTemplate.send(Constants.FIRST_TOPIC, message.getMessage()).addCallback(new ListenableFutureCallback() {
                    @Override
                    public void onFailure(Throwable throwable) {

                        logger.log(Level.INFO, String.format("Message '%s' failed!", message.getMessage()));
                    }

                    @Override
                    public void onSuccess(Object o) {

                        logger.log(Level.INFO, String.format("Message '%s' sent successfully!", message.getMessage()));
                    }
                });
                break;
            case SecondTopic:
                secondKafkaTemplate.send(Constants.SECOND_TOPIC, message.getMessage()).addCallback(new ListenableFutureCallback() {
                    @Override
                    public void onFailure(Throwable throwable) {

                        logger.log(Level.INFO, String.format("Message '%s' failed!", message.getMessage()));
                    }

                    @Override
                    public void onSuccess(Object o) {

                        logger.log(Level.INFO, String.format("Message '%s' sent successfully!", message.getMessage()));
                    }
                });
                break;
        }
        return "Success!";
    }
}
