package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import io.reactivex.Single;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.TelegramResponse;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramAuth implements TelegramCommand<Boolean> {
  private static final String MESSAGE_AUTH = "Authorized!";
  private final TelegramApi api;

  @Inject public TelegramAuth(@NonNull TelegramApi api) {
    this.api = api;
  }

  @NonNull @Override public Single<Boolean> send(@NonNull TelegramParams params) {
    final Map<String, String> nextParams = params.newBuilder().text(MESSAGE_AUTH).build().asMap();
    return api.sendMessage(nextParams).map(TelegramResponse::isOk);
  }
}
