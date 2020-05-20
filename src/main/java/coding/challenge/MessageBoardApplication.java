package coding.challenge;

import coding.challenge.board.MessageBoardFactory;
import java.util.Scanner;
import java.util.Timer;

public class MessageBoardApplication {

  public static void main(String[] args) {

    MessageBoardFactory.createBoard(false, "public-forum", "");
    MessageBoardFactory.createBoard(true, "private-forum", "private");
    Timer timer = new Timer();
    timer.schedule(new MessageProducer(), 0, 5000);
    System.out.println("Welcome to messaging application");
    System.out.println("Please enter your name to proceed:");
    Scanner scanner = new Scanner(System.in);
    String name = scanner.next();
    System.out.println("*****Available Options*****");
    System.out.println("*. Write 'ls' to list all message boards");
    System.out.println("*. Write 'subscribe [BOARD_NAME]'  to subscribe to a board");
    System.out.println("*. Write 'unsubscribe [BOARD_NAME]'  to unsubscribe to a board");
    System.out.println("*. Write 'ea'  to enable or disable the notification in the console");
    System.out.println("*. Write 'notification [EMAIL | SMS | WHAT_APP]' to change preference of notification");
    System.out.println("*. Press 0 to exit");
    String input;
    while (true) {
      System.out.println("Enter your choice:");
      input = scanner.nextLine();
      if (input.equals("0")) {
        System.exit(0);
      } else if (!input.equals("")) {
        Command command = new Command(input, scanner, name);
        command.execute();
      }
    }
  }
}
