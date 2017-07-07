package rm.com.disturb;

import android.support.annotation.NonNull;
import java.util.Locale;

/**
 * Created by alex
 */

final class Formats {
  private Formats() {
  }

  @NonNull static String smsOf(@NonNull String from, @NonNull String text) {
    return String.format(Locale.getDefault(), "%s\n%s", boldOf(from), text);
  }

  @NonNull static String callOf(@NonNull String from) {
    return String.format(Locale.getDefault(), "%s is calling...", boldOf(from));
  }

  @NonNull static String contactNameOf(@NonNull String name, @NonNull String phone) {
    return name.isEmpty() ? phone : (name + ", " + phone);
  }

  @NonNull private static String italicOf(@NonNull String text) {
    return String.format(Locale.getDefault(), "_%s_", text);
  }

  @NonNull private static String boldOf(@NonNull String text) {
    return String.format(Locale.getDefault(), "*%s*", text);
  }
}
