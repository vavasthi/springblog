package in.springframework.blog.tutorials.listeners;

import in.springframework.blog.tutorials.events.UserCreatedEvent;
import in.springframework.blog.tutorials.utils.MyConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
@Log4j2
public class UserCreatedEventListener {

  @Autowired
  @Qualifier(MyConstants.FIRST_TOPIC_TEMPLATE_NAME)
  private KafkaTemplate firstKafkaTemplate;

  @TransactionalEventListener(phase= TransactionPhase.BEFORE_COMMIT)
  void handleBeforeCommit(UserCreatedEvent userCreatedEvent) {
    log.error("Before Commit " + userCreatedEvent);
    sendMessage(userCreatedEvent, "BeforeCommit");
  }
  @TransactionalEventListener(phase= TransactionPhase.AFTER_COMMIT)
  void handleAfterCommit(UserCreatedEvent userCreatedEvent) {

    log.error("After Commit " + userCreatedEvent);
    sendMessage(userCreatedEvent, "AfterCommit");
  }
  @TransactionalEventListener(phase= TransactionPhase.AFTER_ROLLBACK)
  void handleAfterRollback(UserCreatedEvent userCreatedEvent) {

    log.error("After Rollback " + userCreatedEvent);
    sendMessage(userCreatedEvent, "AfterRollback");
  }
  @TransactionalEventListener(phase= TransactionPhase.AFTER_COMPLETION)
  void handleAfterCompletion(UserCreatedEvent userCreatedEvent) {

    log.error("After Completion " + userCreatedEvent);
    sendMessage(userCreatedEvent, "AfterCompletion");
  }
  private void sendMessage(UserCreatedEvent userCreatedEvent, String status) {

    userCreatedEvent.setStatus(status);
    firstKafkaTemplate.send(MyConstants.FIRST_TOPIC, userCreatedEvent.toString()).addCallback(new ListenableFutureCallback() {
      @Override
      public void onFailure(Throwable throwable) {

        log.info(String.format("Message '%s' failed!", userCreatedEvent.toString()));
      }

      @Override
      public void onSuccess(Object o) {

        log.info(String.format("Message '%s' sent successfully!", userCreatedEvent.toString()));
      }
    });
  }
}
