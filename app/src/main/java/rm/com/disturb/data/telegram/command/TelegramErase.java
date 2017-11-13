package rm.com.disturb.data.telegram.command;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import retrofit2.Response;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.response.TelegramResponse;
import rm.com.disturb.inject.qualifier.ChatId;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramErase extends AbstractTelegramCommand<Boolean> {
  private final Provider<String> chatId;

  @Inject TelegramErase(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api, @NonNull @ChatId Provider<String> chatIdProvider) {
    super(executor, mainThreadHandler, api);
    this.chatId = chatIdProvider;
  }

  private boolean isResponseValid(@NonNull Response<TelegramResponse> response) {
    final TelegramResponse body = response.body();
    return body != null && response.isSuccessful() && body.isOk();
  }

  @NonNull @Override Boolean sendBlocking(@NonNull TelegramParams params) throws IOException {
    final Map<String, String> nextParams = params.newBuilder().chatId(chatId.get()).build().asMap();
    final Response<TelegramResponse> response = api.deleteMessage(nextParams).execute();

    return isResponseValid(response);
  }

  @NonNull @Override Boolean defaultResult() {
    return Boolean.FALSE;
  }
}
