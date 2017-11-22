package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import retrofit2.Call;
import retrofit2.Response;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.Message;
import rm.com.disturb.data.telegram.model.TelegramResponse;
import rm.com.disturb.inject.qualifier.ChatId;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramUpdate extends AbstractTelegramCommand<Optional<String>> {
  private final Provider<String> chatId;

  @Inject TelegramUpdate(@NonNull TelegramApi api,
      @NonNull @ChatId Provider<String> chatIdProvider) {
    super(api);
    chatId = chatIdProvider;
  }

  @NonNull @Override Optional<String> sendBlocking(@NonNull TelegramParams params)
      throws IOException {
    final Map<String, String> nextParams = params.newBuilder().chatId(chatId.get()).build().asMap();
    final Call<TelegramResponse<Message>> editMessage = api.editMessage(nextParams);

    final Response<TelegramResponse<Message>> response = editMessage.execute();
    final TelegramResponse<Message> body = response.body();
    final boolean isOk = isResponseBodyValid(body) && response.isSuccessful();

    if (!isOk) {
      return Optional.empty();
    }

    return Optional.ofNullable(String.valueOf(body.data().messageId()));
  }

  private boolean isResponseBodyValid(@Nullable TelegramResponse<Message> body) {
    return body != null && body.isOk() && body.data() != null;
  }
}
