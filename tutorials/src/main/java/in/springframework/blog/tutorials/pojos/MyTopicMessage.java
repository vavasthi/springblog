package in.springframework.blog.tutorials.pojos;

import in.springframework.blog.tutorials.utils.MyConstants;

public class MyTopicMessage {
    public MyTopicMessage() {
    }

    public MyTopicMessage(String message, MyConstants.TOPIC_NAME topicName) {
        this.message = message;this.topicName = topicName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MyConstants.TOPIC_NAME getTopicName() {
        return topicName;
    }

    public void setTopicName(MyConstants.TOPIC_NAME topicName) {
        this.topicName = topicName;
    }

    private String message;
    private MyConstants.TOPIC_NAME topicName;
}
