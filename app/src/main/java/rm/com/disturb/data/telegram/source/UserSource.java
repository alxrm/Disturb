package rm.com.disturb.data.telegram.source;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.concurrent.ExecutorService;
import rm.com.disturb.data.async.PendingResult;
import rm.com.disturb.data.async.Transform;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.UserFull;
import rm.com.disturb.data.telegram.response.ChatResponse;
import rm.com.disturb.utils.Chats;

import static rm.com.disturb.data.telegram.model.UserFull.EMPTY;

/**
 * Created by alex
 */

public final class UserSource implements Source<UserFull, String> {
  final ExecutorService executor;
  final Handler mainThreadHandler;
  final TelegramApi api;
  final Storage<UserFull> userStorage;
  final PendingResult<UserFull> result;

  public UserSource(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api, @NonNull Storage<UserFull> userStorage) {
    this.executor = executor;
    this.mainThreadHandler = mainThreadHandler;
    this.api = api;
    this.userStorage = userStorage;
    this.result = new PendingResult.Builder<>(EMPTY) //
        .executor(executor) //
        .handler(mainThreadHandler) //
        .build();
  }

  @NonNull @Override public PendingResult<UserFull> retrieve(@NonNull final String chatId) {
    return result //
        .map(UserFull.EMPTY, new Transform<UserFull, UserFull>() {
          @NonNull @Override public UserFull apply(@NonNull UserFull input) throws Exception {
            final ChatResponse chatResponse = api.chat(chatId).execute().body();

            if (chatResponse == null) {
              return EMPTY;
            }

            final UserFull userFull = Chats.ofChat(chatResponse.data());

            userStorage.put(chatId, userFull);

            return userFull;
          }
        });
  }
}
