package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import retrofit2.Response;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.Message;
import rm.com.disturb.data.telegram.model.TelegramResponse;
import rm.com.disturb.inject.qualifier.ChatId;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramNotify extends AbstractTelegramCommand<Optional<String>> {

  private final Provider<String> chatId;

  @Inject TelegramNotify(@NonNull TelegramApi api,
      @NonNull @ChatId Provider<String> chatIdProvider) {
    super(api);
    chatId = chatIdProvider;
  }

  @NonNull @Override Optional<String> sendBlocking(@NonNull TelegramParams params)
      throws IOException {
    final Map<String, String> nextParams = params.newBuilder().chatId(chatId.get()).build().asMap();
    final Response<TelegramResponse<Message>> response = api.sendMessage(nextParams).execute();
    final TelegramResponse<Message> body = response.body();
    final boolean isOk = isResponseBodyValid(body) && response.isSuccessful();

    if (!isOk) {
      return Optional.empty();
    }

    return Optional.ofNullable(String.valueOf(body.data().messageId()));
  }

  private boolean isResponseBodyValid(@Nullable TelegramResponse<Message> body) {
    return body != null && body.isOk();
  }
}
