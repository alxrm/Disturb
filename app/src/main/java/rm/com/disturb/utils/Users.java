package rm.com.disturb.utils;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.text.MessageFormat;
import rm.com.disturb.data.telegram.model.Chat;
import rm.com.disturb.data.telegram.model.User;

import static rm.com.disturb.BuildConfig.BOT_TOKEN;
import static rm.com.disturb.data.telegram.TelegramApi.TELEGRAM_FILE_URL;

/**
 * Created by alex
 */

public final class Users {
  public static final int[] AVATAR_ICON_COLORS = {
      0xffe57373, 0xfff06292, 0xffba68c8, 0xff9575cd, 0xff7986cb, 0xff64b5f6, 0xff4fc3f7,
      0xff4dd0e1, 0xff4db6ac, 0xff81c784, 0xffaed581, 0xffff8a65, 0xffd4e157, 0xffffd54f,
      0xffffb74d, 0xffa1887f, 0xff90a4ae
  };

  private Users() {
    throw new IllegalStateException("No instances");
  }

  @NonNull public static User ofChat(@Nullable Chat chat) {
    if (chat == null) {
      return User.EMPTY_USER;
    }

    return new User.Builder().firstName(chat.firstName())
        .lastName(chat.lastName())
        .username(chat.username())
        .build(); //
  }

  @NonNull public static String photoLinkOf(@Nullable String path) {
    return path == null ? ""
        : MessageFormat.format("{0}{1}/{2}", TELEGRAM_FILE_URL, BOT_TOKEN, path);
  }

  @NonNull public static String iconLettersOf(@NonNull String name) {
    return name.substring(0, 1);
  }

  @NonNull public static ColorFilter avatarColorFilterOf(@NonNull String firstName) {
    return new PorterDuffColorFilter(colorOf(firstName), PorterDuff.Mode.MULTIPLY);
  }

  public static int colorOf(@NonNull String name) {
    final int size = AVATAR_ICON_COLORS.length;
    final int nameHash = Math.abs(name.hashCode());

    return AVATAR_ICON_COLORS[nameHash % size];
  }
}
