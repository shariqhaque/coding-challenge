package coding.challenge.board;

import static coding.challenge.utils.StreamUtils.nullSafeStream;

import coding.challenge.problemexception.ProblemBuilder;
import coding.challenge.problemexception.ProblemException;
import coding.challenge.subscriber.SubscribedUser;
import coding.challenge.subscriber.Subscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MessageBoard {
  private final String name;
  private final boolean isPrivate;
  private final String password;
  private final List<Subscriber> subscribers = new ArrayList<>();

  private void hasAccess(String password) throws ProblemException {
    if (isPrivate) {
      Optional.ofNullable(password)
          .filter(this.password::equals)
          .map(s -> true)
          .orElseThrow(ProblemBuilder.passwordIncorrectProblem());
    }
  }

  public void addSubscriber(SubscribedUser subscribedUser, String password)
      throws ProblemException {
    hasAccess(password);
    if (subscribers.contains(subscribedUser)) {
      throw ProblemBuilder.alreadySubscribed();
    }
    subscribers.add(subscribedUser);
  }

  public void setNewMessage(String message) {
    nullSafeStream(subscribers).forEach(subscriber -> subscriber.update(message));
  }

  public void removeSubscriber(String user) {
    subscribers.remove(SubscribedUser.builder().userName(user).build());
  }
}
