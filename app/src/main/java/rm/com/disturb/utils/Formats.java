package rm.com.disturb.utils;

import android.support.annotation.NonNull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by alex
 */

public final class Formats {
  private static final List<String> KEYWORDS =
      Arrays.asList("код", "kod", "code", "пароль", "password", "parol", "ключ", "key", "kluch",
          "klyuch", "klutch", "klyutch", "token", "токен");

  private Formats() {
  }

  @NonNull public static String smsOf(@NonNull String from, @NonNull String text) {
    final String code = extractCodeFrom(text);

    if (!code.isEmpty()) {
      return String.format(Locale.getDefault(), "%s\n_Code: %s_\n%s", boldOf(from), code, text);
    }

    return String.format(Locale.getDefault(), "%s\n%s", boldOf(from), text);
  }

  @NonNull public static String callRingingOf(@NonNull String from) {
    return String.format(Locale.getDefault(), "%s is calling...", boldOf(from));
  }

  @NonNull public static String callMissedOf(@NonNull String from) {
    return String.format(Locale.getDefault(), "Missed call from %s", boldOf(from));
  }

  @NonNull public static String callFinishedOf(@NonNull String from) {
    return String.format(Locale.getDefault(), "Finished call from %s", boldOf(from));
  }

  @NonNull public static String contactNameOf(@NonNull String name, @NonNull String phone) {
    return name.isEmpty() ? phone : (name + ", " + phone);
  }

  @NonNull private static String italicOf(@NonNull String text) {
    return String.format(Locale.getDefault(), "_%s_", text);
  }

  @NonNull private static String boldOf(@NonNull String text) {
    return String.format(Locale.getDefault(), "*%s*", text);
  }

  @NonNull private static String extractCodeFrom(@NonNull String text) {
    final int startIndex = firstKeyWordIndex(text);

    if (startIndex == -1) {
      return "";
    }

    final List<String> words = Lists.listOfArray(text.substring(startIndex).split("(\\s|,|\\.)"));

    return Lists.first(words, "", new Lists.Predicate<String>() {
      @Override public boolean test(String item) {
        return isNonOnlyLetterWord(item);
      }
    });
  }

  private static int firstKeyWordIndex(@NonNull String text) {
    final String lowered = text.toLowerCase();
    final List<Integer> indices = Lists.map(KEYWORDS, new Lists.Transformer<String, Integer>() {
      @Override public Integer invoke(String item) {
        return lowered.indexOf(item);
      }
    });

    Collections.sort(indices);

    return Lists.first(indices, -1, new Lists.Predicate<Integer>() {
      @Override public boolean test(Integer item) {
        return item > -1;
      }
    });
  }

  private static boolean isNonOnlyLetterWord(@NonNull String word) {
    boolean onlyLetters = true;

    for (char item : word.toCharArray()) {
      if (!Character.isLetterOrDigit(item)) {
        return false;
      }

      onlyLetters = onlyLetters && Character.isLetter(item);
    }

    return !onlyLetters;
  }
}