package coding.challenge.problemexception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProblemException extends Exception {
  private final int status;
  private final String message;
}
