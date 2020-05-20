package coding.challenge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import coding.challenge.board.MessageBoard;
import coding.challenge.problemexception.ProblemException;
import coding.challenge.subscriber.NotificationMode;
import coding.challenge.subscriber.SubscribedUser;
import org.junit.Test;

public class ObservationIntegrationTests {

  @Test
  public void shouldUpdateMessage_whenNewMessageArrives_GivenSubscribedToPublicBoard()
      throws ProblemException {
    MessageBoard messageBoard = new MessageBoard("Public", false, null);
    SubscribedUser subscribedUser =
        SubscribedUser.builder()
            .notificationMode(NotificationMode.EMAIL)
            .userName("Shariq")
            .build();
    messageBoard.addSubscriber(subscribedUser, null);
    messageBoard.setNewMessage("hello");

    assertEquals("hello", subscribedUser.getMessage());
  }

  @Test
  public void shouldUpdateMessage_whenNewMessageArrives_GivenSubscribedToPrivateBoard()
      throws ProblemException {
    MessageBoard messageBoard = new MessageBoard("Public", true, "123");
    SubscribedUser subscribedUser =
        SubscribedUser.builder()
            .notificationMode(NotificationMode.EMAIL)
            .userName("Shariq")
            .build();
    messageBoard.addSubscriber(subscribedUser, "123");
    messageBoard.setNewMessage("hello");

    assertEquals("hello", subscribedUser.getMessage());
  }

  @Test(expected = ProblemException.class)
  public void shouldThrowException_whenAddingSubscription_givenPasswordMismatch()
      throws ProblemException {
    MessageBoard messageBoard = new MessageBoard("Public", true, "shariq");
    SubscribedUser subscribedUser =
        SubscribedUser.builder()
            .notificationMode(NotificationMode.EMAIL)
            .userName("Shariq")
            .build();
    messageBoard.addSubscriber(subscribedUser, "123");
  }

  @Test
  public void shouldNotUpdateMessage_whenNewMessageArrive_GivenNotSubscription() {
    MessageBoard messageBoard = new MessageBoard("Public", false, null);
    SubscribedUser subscribedUser =
        SubscribedUser.builder()
            .notificationMode(NotificationMode.EMAIL)
            .userName("Shariq")
            .build();
    messageBoard.setNewMessage("123");
    assertNotEquals("123", subscribedUser.getMessage());
  }

  @Test
  public void shouldNotUpdateMessage_whenNewMessageArrive_GivenSubscriptionRevoked()
      throws ProblemException {
    MessageBoard messageBoard = new MessageBoard("Public", false, null);
    SubscribedUser subscribedUser =
        SubscribedUser.builder()
            .notificationMode(NotificationMode.EMAIL)
            .userName("random")
            .build();
    messageBoard.addSubscriber(subscribedUser, null);
    messageBoard.setNewMessage("hello hello");

    assertEquals("hello hello", subscribedUser.getMessage());
    messageBoard.removeSubscriber("random");
    messageBoard.setNewMessage("hello hello part 2");
    assertNotEquals("hello hello part 2", subscribedUser.getMessage());
  }

  @Test
  public void shouldChangeNotification_whenNewMethodSelected() {
    SubscribedUser subscribedUser =
        SubscribedUser.builder()
            .notificationMode(NotificationMode.EMAIL)
            .userName("random")
            .build();
    assertEquals(NotificationMode.EMAIL, subscribedUser.getNotificationMode());
    subscribedUser.setNotificationMode(NotificationMode.WHAT_APP);
    assertEquals(NotificationMode.WHAT_APP, subscribedUser.getNotificationMode());
  }

  @Test(expected = ProblemException.class)
  public void shouldThrowException_whenSubscribing_givenUserHasAlreadyBeenSubscribed()
      throws ProblemException {
    MessageBoard messageBoard = new MessageBoard("Public", false, null);
    SubscribedUser subscribedUser =
        SubscribedUser.builder()
            .notificationMode(NotificationMode.EMAIL)
            .userName("Shariq")
            .build();
    messageBoard.addSubscriber(subscribedUser, null);
    messageBoard.addSubscriber(subscribedUser, null);
  }
}
