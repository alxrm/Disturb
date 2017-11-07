package rm.com.disturb.data.telegram.command;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import rm.com.disturb.data.async.PendingResult;
import rm.com.disturb.data.telegram.TelegramApi;

/**
 * Created by alex
 */

public abstract class TelegramCommand<T> implements Command<T> {
  final ExecutorService executor;
  final Handler mainThreadHandler;
  final TelegramApi api;
  final PendingResult<T> result;

  public TelegramCommand(@NonNull ExecutorService executor, @NonNull Handler mainThreadHandler,
      @NonNull TelegramApi api) {
    this.executor = executor;
    this.mainThreadHandler = mainThreadHandler;
    this.api = api;
    this.result = new PendingResult.Builder<>(defaultResult()) //
        .executor(executor) //
        .handler(mainThreadHandler) //
        .build();
  }

  @NonNull @Override public PendingResult<T> send(@NonNull TelegramParams params) {
    return result.newBuilder().from(asCallable(params)).build();
  }

  @NonNull abstract T sendBlocking(@NonNull TelegramParams params) throws IOException;

  @NonNull abstract T defaultResult();

  @NonNull private Callable<T> asCallable(@NonNull final TelegramParams params) {
    return new Callable<T>() {
      @Override public T call() throws Exception {
        return sendBlocking(params);
      }
    };
  }
}
