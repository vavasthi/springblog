package in.springframework.blog.tutorials.listeners;

import in.springframework.blog.tutorials.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyTopicKafkaListener {

    @Value("${spring.kafka.consumer.group-id:mygroup}")
    private String groupId;
    Logger logger = LogManager.getLogger(MyTopicKafkaListener.class);
    @KafkaListener(topics = Constants.MY_TOPIC)
    public void processMessage(String content) {

        logger.info("Received Promise message " + content);
    }
}
