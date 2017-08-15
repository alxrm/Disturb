package rm.com.disturb.telegram;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rm.com.disturb.async.AsyncResult;
import rm.com.disturb.telegram.response.MessageResponse;

/**
 * Created by alex
 */

public final class TelegramAuth implements Auth {
  private static final String MESSAGE_AUTH_TEST = "Authorized!";

  @NonNull private final TelegramApi api;

  public TelegramAuth(@NonNull TelegramApi api) {
    this.api = api;
  }

  @WorkerThread @Override //
  public boolean authorize(@NonNull String chatId) {
    try {
      final Response<MessageResponse> response =
          api.sendMessage(chatId, MESSAGE_AUTH_TEST).execute();

      return isSuccess(response);
    } catch (IOException e) {
      return false;
    }
  }

  @Override public void authorizeAsync(@NonNull final String chatId,
      @NonNull final AsyncResult<Boolean> asyncResult) {
    api.sendMessage(chatId, MESSAGE_AUTH_TEST).enqueue(new Callback<MessageResponse>() {
      @Override
      public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
        asyncResult.ready(isSuccess(response));
      }

      @Override public void onFailure(Call<MessageResponse> call, Throwable t) {
        asyncResult.ready(false);
      }
    });
  }

  private boolean isSuccess(@NonNull Response<MessageResponse> response) {
    final MessageResponse body = response.body();
    return body != null && response.isSuccessful() && body.isOk();
  }
}
