package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import io.reactivex.Single;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.TelegramResponse;
import rm.com.disturb.inject.qualifier.ChatId;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramErase implements TelegramCommand<Boolean> {
  private final Provider<String> chatId;
  private final TelegramApi api;

  @Inject TelegramErase(@NonNull TelegramApi api,
      @NonNull @ChatId Provider<String> chatIdProvider) {
    this.api = api;
    this.chatId = chatIdProvider;
  }

  @NonNull @Override public Single<Boolean> send(@NonNull TelegramParams params) {
    final Map<String, String> nextParams = params.newBuilder().chatId(chatId.get()).build().asMap();
    return api.deleteMessage(nextParams).map(TelegramResponse::isOk);
  }
}
