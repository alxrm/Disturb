package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import io.reactivex.Single;
import java.util.Map;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.inject.qualifier.ChatId;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramUpdate implements TelegramCommand<Optional<String>> {
  private final Provider<String> chatId;
  private final TelegramApi api;

  @Inject TelegramUpdate(@NonNull TelegramApi api,
      @NonNull @ChatId Provider<String> chatIdProvider) {
    this.api = api;
    this.chatId = chatIdProvider;
  }

  @NonNull @Override public Single<Optional<String>> send(@NonNull TelegramParams params) {
    final Map<String, String> nextParams = params.newBuilder().chatId(chatId.get()).build().asMap();
    return api.editMessage(nextParams).map(it -> it.isOk() //
        ? Optional.empty() //
        : Optional.ofNullable(String.valueOf(it.data().messageId())));
  }
}
