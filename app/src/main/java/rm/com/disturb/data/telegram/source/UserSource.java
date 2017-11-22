package rm.com.disturb.data.telegram.source;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java8.util.Optional;
import rm.com.disturb.data.async.PendingResult;
import rm.com.disturb.data.async.Transform;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.Chat;
import rm.com.disturb.data.telegram.model.Photo;
import rm.com.disturb.data.telegram.model.User;
import rm.com.disturb.data.telegram.response.ChatResponse;
import rm.com.disturb.data.telegram.response.FileResponse;
import rm.com.disturb.utils.Users;

import static rm.com.disturb.utils.Users.photoLinkOf;

/**
 * Created by alex
 */

public final class UserSource implements Source<User, String> {
  private final TelegramApi api;
  private final Storage<User> userStorage;
  private final PendingResult<User> result;

  public UserSource(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api, @NonNull Storage<User> userStorage) {
    this.api = api;
    this.userStorage = userStorage;
    this.result = new PendingResult.Builder<User>() //
        .executor(executor) //
        .handler(mainThreadHandler) //
        .build();
  }

  @NonNull @Override public PendingResult<User> retrieve(@NonNull final String chatId) {
    if (userStorage.contains(chatId)) {
      return result.newBuilder() //
          .from(() -> userStorage.get(chatId)) //
          .build();
    }

    return result //
        .map(toChat(chatId)) //
        .map(toUser(chatId));
  }

  @NonNull private Transform<User, Chat> toChat(@NonNull final String chatId) {
    return input -> {
      final ChatResponse response = api.chat(chatId).execute().body();

      if (response == null || response.data() == null || !response.isOk()) {
        throw new IOException("Response was unsuccessful");
      }

      final Optional<Chat> chat = Optional.ofNullable(response.data());
      userStorage.put(chatId, Users.ofChat(chat));

      return chat;
    };
  }

  @NonNull private Transform<Chat, User> toUser(@NonNull final String chatId) {
    return input -> {
      final Photo userPhoto = input.map(Chat::photo).orElse(null);

      if (userPhoto == null) {
        return userStorage.get(String.valueOf(chatId));
      }

      final FileResponse response = api.file(userPhoto.smallFileId()).execute().body();

      if (response == null || response.data() == null || !response.isOk()) {
        return userStorage.get(String.valueOf(chatId));
      }

      final User updated = userStorage.get(String.valueOf(chatId))
          .map(it -> it.newBuilder().photoUrl(photoLinkOf(response.data().filePath())).build())
          .get();

      userStorage.put(chatId, updated);

      return Optional.of(updated);
    };
  }
}
