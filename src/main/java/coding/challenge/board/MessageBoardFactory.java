package coding.challenge.board;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class MessageBoardFactory {
  public static List<MessageBoard> messageBoards = new ArrayList<>();

  public static void createBoard(boolean isPrivate, String name, String password) {
    messageBoards.add(
        MessageBoard.builder().isPrivate(isPrivate).name(name).password(password).build());
  }
}
