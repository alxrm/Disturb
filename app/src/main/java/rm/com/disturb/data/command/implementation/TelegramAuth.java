package rm.com.disturb.data.command.implementation;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Singleton;
import retrofit2.Response;
import rm.com.disturb.data.async.AsyncPipeline;
import rm.com.disturb.data.async.AsyncResult;
import rm.com.disturb.data.command.Auth;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.response.MessageResponse;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramAuth implements Auth {
  private static final String MESSAGE_AUTH = "Authorized!";

  private final TelegramApi api;
  private final AsyncPipeline<Boolean> pipeline;

  @Inject TelegramAuth(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api) {
    this.api = api;
    this.pipeline = new AsyncPipeline.Builder<>(false) //
        .executor(executor) //
        .handler(mainThreadHandler) //
        .build();
  }

  @WorkerThread //
  @Override public boolean authorizeBlocking(@NonNull String chatId) {
    try {
      final Response<MessageResponse> response = api.sendMessage(chatId, MESSAGE_AUTH).execute();

      return isResponseValid(response);
    } catch (IOException e) {
      return false;
    }
  }

  @Override
  public void authorizeAsync(@NonNull String chatId, @NonNull AsyncResult<Boolean> asyncResult) {
    pipeline.newBuilder().reply(asyncResult).task(authCallable(chatId)).build().invoke();
  }

  @NonNull private Callable<Boolean> authCallable(@NonNull final String chatId) {
    return new Callable<Boolean>() {
      @Override public Boolean call() throws Exception {
        return authorizeBlocking(chatId);
      }
    };
  }

  private boolean isResponseValid(@NonNull Response<MessageResponse> response) {
    final MessageResponse body = response.body();
    return body != null && response.isSuccessful() && body.isOk();
  }
}
