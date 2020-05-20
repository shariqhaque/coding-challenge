package coding.challenge.subscriber;

import coding.challenge.Command;

public enum NotificationMode {
  EMAIL {
    @Override
    public void sendMessage(String message) {
      if (Command.alertEnable)
        System.out.println(String.format("You have a new email %s", message));
    }
  },
  WHAT_APP {
    @Override
    public void sendMessage(String message) {
      if (Command.alertEnable)
        System.out.println(String.format("You have a new whats app %s", message));
    }
  },
  SMS {
    @Override
    public void sendMessage(String message) {
      if (Command.alertEnable) System.out.println(String.format("You have a new SMS %s", message));
    }
  };

  public void sendMessage(String message) {}
}
