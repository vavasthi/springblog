package in.springframework.blog.tutorials.listeners;

import in.springframework.blog.tutorials.Constants;
import in.springframework.blog.tutorials.pojos.MyTopicMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class FirstTopicKafkaListener {

    Logger logger = LogManager.getLogger(FirstTopicKafkaListener.class);
    @KafkaListener(topics = "FirstTopic, SecondTopic")
    public void processMessage(@Payload String content,
                               @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        logger.info(String.format("Received %s message for partition %d", topic, partition) + content);
    }
}
