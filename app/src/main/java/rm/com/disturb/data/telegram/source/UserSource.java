package rm.com.disturb.data.telegram.source;

import android.support.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.concurrent.Callable;
import java8.util.Optional;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.Chat;
import rm.com.disturb.data.telegram.model.FileData;
import rm.com.disturb.data.telegram.model.Photo;
import rm.com.disturb.data.telegram.model.TelegramResponse;
import rm.com.disturb.data.telegram.model.User;
import rm.com.disturb.utils.UserFormats;

import static rm.com.disturb.utils.UserFormats.photoLinkOf;

/**
 * Created by alex
 */

public final class UserSource implements Source<User, String> {
  private final TelegramApi api;
  private final Storage<User> userStorage;

  public UserSource(@NonNull TelegramApi api, @NonNull Storage<User> userStorage) {
    this.api = api;
    this.userStorage = userStorage;
  }

  @NonNull @Override public Flowable<Optional<User>> retrieve(@NonNull String chatId) {
    if (userStorage.contains(chatId)) {
      Flowable.fromCallable(() -> userStorage.get(chatId));
    }

    return Flowable.fromCallable(chatFor(chatId))
        .map(toUser(chatId))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io());
  }

  @NonNull private Callable<Optional<Chat>> chatFor(@NonNull String chatId) {
    return () -> {
      final TelegramResponse<Chat> response = api.chat(chatId).execute().body();

      if (response == null || response.data() == null || !response.isOk()) {
        throw new IOException("Response was unsuccessful");
      }

      final Optional<Chat> chat = Optional.ofNullable(response.data());
      userStorage.put(chatId, UserFormats.ofChat(chat));

      return chat;
    };
  }

  @NonNull private Function<Optional<Chat>, Optional<User>> toUser(@NonNull String chatId) {
    return input -> {
      final Photo userPhoto = input.map(Chat::photo).orElse(null);

      if (userPhoto == null) {
        return userStorage.get(String.valueOf(chatId));
      }

      final TelegramResponse<FileData> response = api.file(userPhoto.smallFileId()).execute().body();

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
