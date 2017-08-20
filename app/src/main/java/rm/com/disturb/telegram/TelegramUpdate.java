package rm.com.disturb.telegram;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import javax.inject.Provider;
import rm.com.disturb.async.AsyncPipeline;
import rm.com.disturb.async.AsyncResult;

/**
 * Created by alex
 */

public final class TelegramUpdate implements Update {
  private static final String EMPTY_MESSAGE_ID = "-1";

  private final TelegramApi api;
  private final String chatId;
  private final AsyncPipeline<String> pipeline;

  public TelegramUpdate(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api, @NonNull Provider<String> chatIdProvider) {
    this.api = api;
    this.chatId = chatIdProvider.get();
    this.pipeline = new AsyncPipeline.Builder<>(EMPTY_MESSAGE_ID) //
        .executor(executor) //
        .handler(mainThreadHandler) //
        .build();
  }

  @Override public void sendAsync(@NonNull String messageId, @NonNull String message) {
    pipeline.newBuilder().task(updateMessageCallable(messageId, message)).build().invoke();
  }

  @Override public void sendAsync(@NonNull String messageId, @NonNull String message,
      @NonNull AsyncResult<String> result) {
    pipeline.newBuilder()
        .task(updateMessageCallable(messageId, message))
        .reply(result)
        .build()
        .invoke();
  }

  @NonNull @Override
  public String sendBlocking(@NonNull String messageId, @NonNull String message) {
    return EMPTY_MESSAGE_ID;
  }

  @NonNull private Callable<String> updateMessageCallable( //
      @NonNull final String messageId, //
      @NonNull final String message //
  ) {
    return new Callable<String>() {
      @Override public String call() throws Exception {
        return sendBlocking(messageId, message);
      }
    };
  }
}
