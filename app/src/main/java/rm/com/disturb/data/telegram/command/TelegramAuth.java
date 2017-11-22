package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Response;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.Message;
import rm.com.disturb.data.telegram.model.TelegramResponse;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramAuth extends AbstractTelegramCommand<Boolean> {
  private static final String MESSAGE_AUTH = "Authorized!";

  @Inject public TelegramAuth(@NonNull TelegramApi api) {
    super(api);
  }

  @NonNull @Override Boolean sendBlocking(@NonNull TelegramParams params) throws IOException {
    final Map<String, String> nextParams = params.newBuilder().text(MESSAGE_AUTH).build().asMap();

    return isResponseValid(api.sendMessage(nextParams).execute());
  }

  private boolean isResponseValid(@NonNull Response<TelegramResponse<Message>> response) {
    final TelegramResponse<Message> body = response.body();
    return body != null && response.isSuccessful() && body.isOk();
  }
}
