package rm.com.disturb.command.implementation;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import retrofit2.Response;
import rm.com.disturb.async.AsyncPipeline;
import rm.com.disturb.async.AsyncResult;
import rm.com.disturb.command.Erase;
import rm.com.disturb.storage.ChatId;
import rm.com.disturb.telegram.TelegramApi;
import rm.com.disturb.telegram.response.TelegramResponse;

/**
 * Created by alex
 */

@Singleton //
public final class TelegramErase implements Erase {
  private final TelegramApi api;
  private final AsyncPipeline<Boolean> pipeline;
  private final String chatId;

  @Inject TelegramErase(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api, @NonNull @ChatId Provider<String> chatIdProvider) {
    this.api = api;
    this.chatId = chatIdProvider.get();
    this.pipeline = new AsyncPipeline.Builder<>(false) //
        .executor(executor) //
        .handler(mainThreadHandler) //
        .build();
  }

  @WorkerThread //
  @Override public boolean deleteBlocking(@NonNull String messageId) {
    try {
      final Response<TelegramResponse> response = api.deleteMessage(chatId, messageId).execute();

      return isResponseValid(response);
    } catch (IOException e) {
      return false;
    }
  }

  @Override public void deleteAsync(@NonNull String messageId) {
    pipeline.newBuilder().task(deleteMessageCallable(messageId)).build().invoke();
  }

  @Override
  public void deleteAsync(@NonNull String messageId, @NonNull AsyncResult<Boolean> result) {
    pipeline.newBuilder().task(deleteMessageCallable(messageId)).reply(result).build().invoke();
  }

  @NonNull private Callable<Boolean> deleteMessageCallable(@NonNull final String messageId) {
    return new Callable<Boolean>() {
      @Override public Boolean call() throws Exception {
        return deleteBlocking(messageId);
      }
    };
  }

  private boolean isResponseValid(@NonNull Response<TelegramResponse> response) {
    final TelegramResponse body = response.body();
    return body != null && response.isSuccessful() && body.isOk();
  }
}
