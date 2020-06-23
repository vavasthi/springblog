package in.springframework.blog.tutorials.endpoints;

import in.springframework.blog.tutorials.utils.MyConstants;
import in.springframework.blog.tutorials.pojos.MyTopicMessage;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@RestController
@RequestMapping("/send")
public class MyTopicMessageSenderEndpoint {

    @Autowired
    @Qualifier(MyConstants.FIRST_TOPIC_TEMPLATE_NAME)
    private KafkaTemplate firstKafkaTemplate;

    @Autowired
    @Qualifier(MyConstants.SECOND_TOPIC_TEMPLATE_NAME)
    private KafkaTemplate secondKafkaTemplate;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize(MyConstants.ANNOTATION_ROLE_USER)
    public String sendMessage(@RequestBody MyTopicMessage message) {

        switch(message.getTopicName()) {
            case FirstTopic:
                firstKafkaTemplate.send(MyConstants.FIRST_TOPIC, message.getMessage()).addCallback(new ListenableFutureCallback() {
                    @Override
                    public void onFailure(Throwable throwable) {

                        log.info(String.format("Message '%s' failed!", message.getMessage()));
                    }

                    @Override
                    public void onSuccess(Object o) {

                        log.info(String.format("Message '%s' sent successfully!", message.getMessage()));
                    }
                });
                break;
            case SecondTopic:
                secondKafkaTemplate.send(MyConstants.SECOND_TOPIC, message.getMessage()).addCallback(new ListenableFutureCallback() {
                    @Override
                    public void onFailure(Throwable throwable) {

                        log.info(String.format("Message '%s' failed!", message.getMessage()));
                    }

                    @Override
                    public void onSuccess(Object o) {

                        log.info(String.format("Message '%s' sent successfully!", message.getMessage()));
                    }
                });
                break;
        }
        return "Success!";
    }
}
