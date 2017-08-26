package rm.com.disturb.data.async;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alex
 */

public final class PendingResult<T> {
  private final ExecutorService executor;
  private final Handler handler;
  private final Callable<T> task;
  private final T defaultResult;

  private PendingResult(@NonNull PendingResult.Builder<T> builder) {
    executor = builder.executor;
    handler = builder.handler;
    task = builder.task;
    defaultResult = builder.defaultResult;
  }

  public void whenReady(@Nullable final Reply<T> reply) {
    executor.submit(new Runnable() {
      @Override public void run() {
        final T result = await();

        replyToHandler(reply, result);
      }
    });
  }

  public void forget() {
    whenReady(null);
  }

  @NonNull public T await() {
    try {
      return task.call();
    } catch (RuntimeException e) {
      throw new RuntimeException(e);
    } catch (Exception e) {
      return defaultResult;
    }
  }

  @NonNull public Builder<T> newBuilder() {
    return new Builder<>(this);
  }

  private void replyToHandler(@Nullable final Reply<T> reply, @NonNull final T result) {
    if (reply == null) {
      return;
    }

    handler.post(new Runnable() {
      @Override public void run() {
        reply.ready(result);
      }
    });
  }

  public static final class Builder<T> {
    private static final ExecutorService DEFAULT_EXECUTOR = Executors.newSingleThreadExecutor();
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    final Callable<T> EMPTY_TASK = new Callable<T>() {
      @Override public T call() throws Exception {
        return defaultResult;
      }
    };

    ExecutorService executor;
    Handler handler;
    Callable<T> task;
    T defaultResult;

    public Builder(@NonNull T nextDefaultResult) {
      executor = DEFAULT_EXECUTOR;
      handler = HANDLER;
      defaultResult = nextDefaultResult;
      task = EMPTY_TASK;
    }

    Builder(@NonNull PendingResult<T> current) {
      executor = current.executor;
      handler = current.handler;
      task = current.task;
      defaultResult = current.defaultResult;
    }

    @NonNull public Builder<T> defaultResult(@NonNull T defaultResult) {
      this.defaultResult = defaultResult;
      return this;
    }

    @NonNull public Builder<T> executor(@NonNull ExecutorService executor) {
      this.executor = executor;
      return this;
    }

    @NonNull public Builder<T> handler(@NonNull Handler handler) {
      this.handler = handler;
      return this;
    }

    @NonNull public Builder<T> task(@NonNull Callable<T> task) {
      this.task = task;
      return this;
    }

    @NonNull public PendingResult<T> build() {
      return new PendingResult<>(this);
    }
  }
}
