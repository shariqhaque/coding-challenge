package coding.challenge;

import static coding.challenge.board.MessageBoardFactory.messageBoards;
import static coding.challenge.subscriber.NotificationMode.*;
import static coding.challenge.utils.StreamUtils.nullSafeStream;

import coding.challenge.board.MessageBoard;
import coding.challenge.problemexception.ProblemException;
import coding.challenge.subscriber.NotificationMode;
import coding.challenge.subscriber.SubscribedUser;
import java.util.List;
import java.util.Scanner;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Command {
  private final String option;
  private final Scanner scanner;
  private final String user;
  public static boolean alertEnable;

  public void execute() {
    String[] subcommands = option.split(" ");
    if (this.option.equals("ls")) {
      nullSafeStream(messageBoards).map(MessageBoard::getName).forEach(System.out::println);
    } else if ("ea".equals(option)) {
      alertEnable = !alertEnable;
    } else if (subcommands.length == 2 && subcommands[0].equals("subscribe")) {
      subscribe(subcommands[1]);

    } else if (subcommands.length == 2 && subcommands[0].equals("unsubscribe")) {
      unsubscribe(subcommands[1]);
    } else if (subcommands.length == 2 && subcommands[0].equals("notification")) {
      messageBoards
          .stream()
          .map(MessageBoard::getSubscribers)
          .flatMap(List::stream)
          .filter(subscriber -> subscriber instanceof SubscribedUser)
          .map(subscriber -> (SubscribedUser) subscriber)
          .filter(subscriber -> user.equals(subscriber.getUserName()))
          .forEach(
              subscribedUser ->
                  subscribedUser.setNotificationMode(NotificationMode.valueOf(subcommands[1])));

    } else {
      System.out.println("Invalid command");
    }
  }

  private void subscribe(String subcommand) {
    nullSafeStream(messageBoards)
        .filter(messageBoard -> messageBoard.getName().equals(subcommand))
        .findFirst()
        .ifPresentOrElse(
            messageBoard -> {
              String password = null;
              if (messageBoard.isPrivate()) {
                System.out.println("Please enter the password for subscription:");
                password = scanner.next();
              }

              System.out.println(
                  String.format(
                      "Choose %s,%s or %s as your notification medium", EMAIL, WHAT_APP, SMS));
              String next = scanner.next();

              try {
                messageBoard.addSubscriber(
                    SubscribedUser.builder()
                        .userName(user)
                        .notificationMode(NotificationMode.valueOf(next))
                        .build(),
                    password);
              } catch (ProblemException e) {
                System.out.println(e.getMessage());
                return;
              }
            },
            () -> System.out.println(String.format("No board found with %s", subcommand)));
  }

  private void unsubscribe(@NonNull String subcommand) {
    nullSafeStream(messageBoards)
        .filter(messageBoard -> messageBoard.getName().equals(subcommand))
        .findFirst()
        .ifPresentOrElse(
            messageBoard -> messageBoard.removeSubscriber(user),
            () ->
                System.out.println(
                    String.format("Either message board with name %s doesn't exist", subcommand)));
  }
}
