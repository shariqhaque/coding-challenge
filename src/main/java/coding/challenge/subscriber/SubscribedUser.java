package coding.challenge.subscriber;

import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode(of = "userName")
public class SubscribedUser implements Subscriber {

  private String userName;
  private NotificationMode notificationMode;
  private String message;

  @Override
  public void update(String receivedMessage) {
    Optional.ofNullable(receivedMessage)
        .ifPresent(
            message -> {
              this.message = message;
              notificationMode.sendMessage(this.message);
            });
  }
}
