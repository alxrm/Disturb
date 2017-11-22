package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import retrofit2.Response;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.TelegramResponse;
import rm.com.disturb.inject.qualifier.ChatId;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramErase extends AbstractTelegramCommand<Boolean> {
  private final Provider<String> chatId;

  @Inject TelegramErase(@NonNull TelegramApi api,
      @NonNull @ChatId Provider<String> chatIdProvider) {
    super(api);
    this.chatId = chatIdProvider;
  }

  @NonNull @Override Boolean sendBlocking(@NonNull TelegramParams params) throws IOException {
    final Map<String, String> nextParams = params.newBuilder().chatId(chatId.get()).build().asMap();
    final Response<TelegramResponse> response = api.deleteMessage(nextParams).execute();

    return isResponseValid(response);
  }

  private boolean isResponseValid(@NonNull Response<TelegramResponse> response) {
    final TelegramResponse body = response.body();
    return body != null && response.isSuccessful() && body.isOk();
  }
}
