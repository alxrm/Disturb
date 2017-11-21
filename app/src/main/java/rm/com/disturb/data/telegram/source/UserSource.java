package rm.com.disturb.data.telegram.source;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
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

import static rm.com.disturb.data.telegram.model.User.EMPTY_USER;

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
    this.result = new PendingResult.Builder<>(EMPTY_USER) //
        .executor(executor) //
        .handler(mainThreadHandler) //
        .build();
  }

  @NonNull @Override public PendingResult<User> retrieve(@NonNull final String chatId) {
    if (userStorage.contains(chatId)) {
      return result.newBuilder() //
          .from(new Callable<User>() {
            @Override public User call() throws Exception {
              return userStorage.get(chatId);
            }
          }) //
          .build();
    }

    return result //
        .map(new Chat(), toChat(chatId)) //
        .map(EMPTY_USER, toUser(chatId));
  }

  @NonNull private Transform<User, Chat> toChat(@NonNull final String chatId) {
    return new Transform<User, Chat>() {
      @NonNull @Override public Chat apply(@NonNull User input) throws Exception {
        final ChatResponse response = api.chat(chatId).execute().body();

        if (response == null || response.data() == null || !response.isOk()) {
          throw new IOException("Response was unsuccessful");
        }

        final Chat chat = response.data();
        final User user = Users.ofChat(chat);

        userStorage.put(chatId, user);

        return chat;
      }
    };
  }

  @NonNull private Transform<Chat, User> toUser(@NonNull final String chatId) {
    return new Transform<Chat, User>() {
      @NonNull @Override public User apply(@NonNull Chat input) throws Exception {
        final Photo userPhoto = input.photo();

        if (userPhoto == null) {
          return userStorage.get(String.valueOf(chatId), EMPTY_USER);
        }

        final FileResponse response = api.file(userPhoto.smallFileId()).execute().body();

        if (response == null || response.data() == null || !response.isOk()) {
          return userStorage.get(String.valueOf(chatId), EMPTY_USER);
        }

        //noinspection ConstantConditions
        final User updated = userStorage.get(String.valueOf(chatId))
            .newBuilder()
            .photoUrl(Users.photoLinkOf(response.data().filePath()))
            .build();

        userStorage.put(chatId, updated);

        return updated;
      }
    };
  }
}
