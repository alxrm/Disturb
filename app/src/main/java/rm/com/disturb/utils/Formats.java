package rm.com.disturb.utils;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by alex
 */

public final class Formats {

  // weird thing is done on purpose, bc \w doesn't work with Russian
  private static final String WORDS_PATTERN = "[А-Яа-яA-Za-z0-9]{3,}";

  private static final HashSet<String> KEYWORDS = new HashSet<>(
      Arrays.asList("код", "kod", "code", "пароль", "password", "parol", "ключ", "key", "kluch",
          "klyuch", "klutch", "klyutch", "token", "токен"));

  private Formats() {
    throw new IllegalStateException("No instances");
  }

  @NonNull public static String smsOf(@NonNull String from, @NonNull String text,
      boolean withCodesMagnification) {
    final String mdFreeText = escapeMarkdown(text);
    final String code = magnifyCodeIn(mdFreeText);

    if (!code.isEmpty() && withCodesMagnification) {
      return String.format(Locale.getDefault(), "%s\n_Code: %s_\n%s", boldOf(from), code,
          mdFreeText);
    }

    return String.format(Locale.getDefault(), "%s\n%s", boldOf(from), mdFreeText);
  }

  @NonNull public static String callRingingOf(@NonNull String from) {
    return String.format(Locale.getDefault(), "%s is calling...", boldOf(from));
  }

  @NonNull public static String callMissedOf(@NonNull String from) {
    return String.format(Locale.getDefault(), "Missed call: %s", boldOf(from));
  }

  @NonNull public static String callFinishedOf(@NonNull String from) {
    return String.format(Locale.getDefault(), "Finished call: %s", boldOf(from));
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

  @NonNull private static String escapeMarkdown(@NonNull String text) {
    return text.replaceAll("\\*", "\\*").replaceAll("_", "\\_");
  }

  @NonNull private static String magnifyCodeIn(@NonNull String text) {
    final List<String> words = asWords(text);
    boolean keywordMet = false;

    System.out.println(words);

    for (String word : words) {
      if (KEYWORDS.contains(word.toLowerCase())) {
        keywordMet = true;
        continue;
      }

      if (keywordMet && isNonOnlyLetterWord(word)) {
        return word;
      }
    }

    return "";
  }

  @NonNull private static List<String> asWords(@NonNull String text) {
    final Pattern pattern = Pattern.compile(WORDS_PATTERN, Pattern.CASE_INSENSITIVE);
    final List<String> words = new ArrayList<>(text.length() / 2);
    final Matcher matcher = pattern.matcher(text);

    while (matcher.find()) {
      words.add(matcher.group());
    }

    return words;
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