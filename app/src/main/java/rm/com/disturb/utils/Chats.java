package rm.com.disturb.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import rm.com.disturb.data.telegram.model.Chat;
import rm.com.disturb.data.telegram.model.UserFull;

/**
 * Created by alex
 */

public final class Chats {
  private Chats() {
    throw new IllegalStateException("No instances");
  }

  @NonNull public static UserFull ofChat(@Nullable Chat chat) {
    if (chat == null) {
      return UserFull.EMPTY;
    }

    return new UserFull.Builder().firstName(chat.firstName())
        .lastName(chat.lastName())
        .username(chat.username())
        .build();
  }
}
