package rm.com.disturb.data.telegram.command;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import retrofit2.Call;
import retrofit2.Response;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.response.MessageResponse;
import rm.com.disturb.inject.qualifier.ChatId;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramUpdate extends TelegramCommand<String> {
  private static final String EMPTY_MESSAGE_ID = "-1";

  private final String chatId;

  @Inject TelegramUpdate(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api, @NonNull @ChatId Provider<String> chatIdProvider) {
    super(executor, mainThreadHandler, api);
    chatId = chatIdProvider.get();
  }

  @NonNull @Override String sendBlocking(@NonNull TelegramParams params) throws IOException {
    final Map<String, String> nextParams = params.newBuilder().chatId(chatId).build().asMap();
    final Call<MessageResponse> editMessage = api.editMessage(nextParams);

    final Response<MessageResponse> response = editMessage.execute();
    final MessageResponse body = response.body();
    final boolean isOk = isResponseBodyValid(body) && response.isSuccessful();

    if (!isOk) {
      return EMPTY_MESSAGE_ID;
    }

    return String.valueOf(body.message().messageId());
  }

  @NonNull @Override String defaultResult() {
    return EMPTY_MESSAGE_ID;
  }

  private boolean isResponseBodyValid(@Nullable MessageResponse body) {
    return body != null && body.isOk() && body.message() != null;
  }
}
