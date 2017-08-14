package rm.com.disturb.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by alex
 */

public final class Preconditions {
  private Preconditions() {
    throw new IllegalStateException("No instances");
  }

  public static void check(boolean clause) {
    if (!clause) {
      throw new IllegalStateException("Check failed");
    }
  }

  public static void check(boolean clause, @NonNull String message) {
    if (!clause) {
      throw new IllegalStateException("Check failed: " + message);
    }
  }

  public static <T> T checkNotNull(@Nullable T reference) {
    if (reference == null) {
      throw new NullPointerException("Element should not be null");
    }

    return reference;
  }

  public static <T> T checkNotNull(@Nullable T reference, @NonNull String message) {
    if (reference == null) {
      throw new NullPointerException("Element should not be null: " + message);
    }

    return reference;
  }
}
