package coding.challenge.problemexception;

import java.util.function.Supplier;

public class ProblemBuilder {

  public static Supplier<ProblemException> passwordIncorrectProblem() {
    return () -> new ProblemException(401, "Password mismatch");
  }

  public static ProblemException alreadySubscribed() {
    return new ProblemException(412, "User is already subscribed");
  }
}
