package in.springframework.blog.tutorials.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageListener {

    Logger logger = LogManager.getLogger(KafkaMessageListener.class);
    @KafkaListener(topics = "FirstTopic", containerFactory = "firstTopicListenerContainerFactory")
    public void processFirstTopicMessage(@Payload String content,
                                         @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                         @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        logger.info(String.format("Received %s message for partition %d ", topic, partition) + content);
    }
    @KafkaListener(topics = "SecondTopic", containerFactory = "secondTopicListenerContainerFactory")
    public void processSecondTopicMessage(@Payload String content,
                                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        logger.info(String.format("Received %s message for partition %d ", topic, partition) + content);
    }
}
