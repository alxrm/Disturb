package rm.com.disturb.utils;

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
        .build();
  }

  @NonNull public static String photoLinkOf(@Nullable String path) {
    return path == null ? ""
        : MessageFormat.format("{0}{1}/{2}", TELEGRAM_FILE_URL, BOT_TOKEN, path);
  }
}
