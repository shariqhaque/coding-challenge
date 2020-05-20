package coding.challenge;

import coding.challenge.board.MessageBoardFactory;
import java.util.TimerTask;
import java.util.UUID;

public class MessageProducer extends TimerTask {
  @Override
  public void run() {
    MessageBoardFactory.messageBoards.forEach(
        messageBoard -> messageBoard.setNewMessage(UUID.randomUUID().toString().replace("-", "")));
  }
}
