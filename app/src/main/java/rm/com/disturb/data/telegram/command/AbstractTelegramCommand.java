package rm.com.disturb.data.telegram.command;

import android.os.Handler;
import android.support.annotation.NonNull;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java8.util.Optional;
import rm.com.disturb.data.async.PendingResult;
import rm.com.disturb.data.telegram.TelegramApi;

import static java8.util.Optional.ofNullable;

/**
 * Created by alex
 */

public abstract class AbstractTelegramCommand<T> implements TelegramCommand<T> {
  final ExecutorService executor;
  final Handler mainThreadHandler;
  final TelegramApi api;
  final PendingResult<T> result;

  public AbstractTelegramCommand(@NonNull ExecutorService executor,
      @NonNull Handler mainThreadHandler, @NonNull TelegramApi api) {
    this.executor = executor;
    this.mainThreadHandler = mainThreadHandler;
    this.api = api;
    this.result = new PendingResult.Builder<T>() //
        .executor(executor) //
        .handler(mainThreadHandler) //
        .build();
  }

  @NonNull @Override public PendingResult<T> send(@NonNull TelegramParams params) {
    return result.newBuilder()
        .from(() -> ofNullable(sendBlocking(params)).or(() -> Optional.of(defaultResult())))
        .build();
  }

  @NonNull abstract T sendBlocking(@NonNull TelegramParams params) throws IOException;

  @NonNull abstract T defaultResult();
}
