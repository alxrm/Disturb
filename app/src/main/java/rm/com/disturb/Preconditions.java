package rm.com.disturb;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

final class Preconditions {

  private Preconditions() {
  }

  static void check(boolean clause) {
    if (!clause) {
      throw new IllegalStateException("Check failed");
    }
  }

  static void check(boolean clause, @NonNull String message) {
    if (!clause) {
      throw new IllegalStateException("Check failed: " + message);
    }
  }

  static <T> T checkNotNull(@Nullable T reference) {
    if (reference == null) {
      throw new NullPointerException("Element should not be null");
    }

    return reference;
  }

  static <T> T checkNotNull(@Nullable T reference, @NonNull String message) {
    if (reference == null) {
      throw new NullPointerException("Element should not be null: " + message);
    }

    return reference;
  }
}