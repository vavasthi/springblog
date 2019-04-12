package in.springframework.blog.tutorials.pojos;

import in.springframework.blog.tutorials.Constants;

public class MyTopicMessage {
    public MyTopicMessage() {
    }

    public MyTopicMessage(String message, Constants.TOPIC_NAME topicName) {
        this.message = message;this.topicName = topicName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Constants.TOPIC_NAME getTopicName() {
        return topicName;
    }

    public void setTopicName(Constants.TOPIC_NAME topicName) {
        this.topicName = topicName;
    }

    private String message;
    private Constants.TOPIC_NAME topicName;
}
