package rm.com.disturb.data.telegram.command;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Response;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.response.MessageResponse;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramAuth extends TelegramCommand<Boolean> {
  private static final String MESSAGE_AUTH = "Authorized!";

  @Inject public TelegramAuth(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api) {
    super(executor, mainThreadHandler, api);
  }

  @NonNull @Override Boolean sendBlocking(@NonNull TelegramParams params) throws IOException {
    final Map<String, String> nextParams = params.newBuilder().text(MESSAGE_AUTH).build().asMap();

    return isResponseValid(api.sendMessage(nextParams).execute());
  }

  @NonNull @Override Boolean defaultResult() {
    return Boolean.FALSE;
  }

  private boolean isResponseValid(@NonNull Response<MessageResponse> response) {
    final MessageResponse body = response.body();
    return body != null && response.isSuccessful() && body.isOk();
  }
}
