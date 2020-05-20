package coding.challenge.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamUtils {

  private StreamUtils() {}

  public static <T> Stream<T> nullSafeStream(Collection<T> collection) {
    return emptyIfNull(collection).stream();
  }

  public static <T> Collection<T> emptyIfNull(Collection<T> collection) {
    return Optional.ofNullable(collection).orElseGet(ArrayList::new);
  }
}
