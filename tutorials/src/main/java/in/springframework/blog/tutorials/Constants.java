package in.springframework.blog.tutorials;

public class Constants {

  public enum TOPIC_NAME {
    FirstTopic,
    SecondTopic
  }

  public static final String FIRST_TOPIC = TOPIC_NAME.FirstTopic.name();
  public static final String SECOND_TOPIC = TOPIC_NAME.SecondTopic.name();
  public static final String FIRST_TOPIC_TEMPLATE_NAME = "firstTopicTemplateName";
  public static final String SECOND_TOPIC_TEMPLATE_NAME = "secondTopicTemplateName";
}
