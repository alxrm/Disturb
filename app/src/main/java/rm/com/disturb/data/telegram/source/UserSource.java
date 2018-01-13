package rm.com.disturb.data.telegram.source;

import android.support.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java8.util.Optional;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.Photo;
import rm.com.disturb.data.telegram.model.TelegramResponse;
import rm.com.disturb.data.telegram.model.User;
import rm.com.disturb.utils.Users;

import static rm.com.disturb.utils.Users.photoLinkOf;

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

  @NonNull @Override public Single<Optional<User>> retrieve(@NonNull String chatId) {
    if (userStorage.contains(chatId)) {
      return Single.fromCallable(() -> userStorage.get(chatId));
    }

    return api.chat(chatId)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .filter(TelegramResponse::isViable)
        .map(TelegramResponse::data)
        .map(it -> {
          userStorage.put(chatId, Users.ofChat(it));
          return it;
        })
        .map(it -> Optional.ofNullable(it.photo()))
        .filter(Optional::isPresent)
        .map(it -> it.map(Photo::smallFileId).get())
        .flatMapSingle(api::file)
        .filter(TelegramResponse::isViable)
        .map(it -> it.data().filePath())
        .map(photoPath -> userStorage.get(String.valueOf(chatId))
            .map(it -> it.newBuilder().photoUrl(photoLinkOf(photoPath)).build())
            .get())
        .map(it -> {
          userStorage.put(chatId, it);
          return Optional.of(it);
        })
        .switchIfEmpty(Single.fromCallable(() -> userStorage.get(chatId)));
  }
}
