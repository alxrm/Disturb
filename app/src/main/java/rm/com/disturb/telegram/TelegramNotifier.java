package rm.com.disturb.telegram;

import android.support.annotation.NonNull;
import javax.inject.Provider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rm.com.disturb.telegram.response.MessageResponse;

/**
 * Created by alex
 */

public final class TelegramNotifier implements Notifier {
  @NonNull private final TelegramApi api;
  @NonNull private final String chatId;

  public TelegramNotifier(@NonNull TelegramApi api, @NonNull Provider<String> chatIdProvider) {
    this.api = api;
    this.chatId = chatIdProvider.get();
  }

  @Override public void notify(@NonNull final String message) {
    if (chatId.isEmpty()) {
      return;
    }

    api.sendMessage(chatId, message).enqueue(new Callback<MessageResponse>() {
      @Override
      public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

      }

      @Override public void onFailure(Call<MessageResponse> call, Throwable t) {

      }
    });
  }
}
