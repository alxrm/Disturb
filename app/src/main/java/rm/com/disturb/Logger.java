package rm.com.disturb;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by alex
 */
@SuppressWarnings("ALL") final class Logger {
  private static final String LEVEL_DEBUG = "DEBUG";
  private static final String LEVEL_ERROR = "ERROR";

  private Logger() {
  }

  static void d(@Nullable Object msg) {
    d(String.valueOf(msg != null ? msg : "null"));
  }

  static void d(@Nullable Object... msgs) {
    final StringBuilder msgBuilder = new StringBuilder();

    for (int i = 0; i < msgs.length; i++) {
      final String currentMsg = msgs[i] == null ? "null" : String.valueOf(msgs[i]);
      final String separator = i == msgs.length - 1 ? "" : ", ";

      msgBuilder.append(currentMsg).append(separator);
    }

    d(msgBuilder.toString());
  }

  static void d(@Nullable String msg) {
    Log.e(LEVEL_DEBUG, "" + msg);
  }

  static void e(@Nullable String err) {
    Log.e(LEVEL_ERROR, "" + err);
  }

  static void e(@NonNull Throwable err) {
    e(String.format("%s: %s", err.getClass().getName(), err.getMessage()));
  }
}