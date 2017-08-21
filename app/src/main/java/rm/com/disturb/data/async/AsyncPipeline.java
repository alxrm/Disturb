package rm.com.disturb.data.async;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by alex
 */

public final class AsyncPipeline<T> {
  final ExecutorService executor;
  final Handler handler;
  final Callable<T> task;
  final AsyncResult<T> reply;
  final T defaultResult;

  private AsyncPipeline(@NonNull AsyncPipeline.Builder<T> builder) {
    executor = builder.executor;
    handler = builder.handler;
    task = builder.task;
    reply = builder.reply;
    defaultResult = builder.defaultResult;
  }

  public void invoke() {
    executor.submit(new Runnable() {
      @Override public void run() {
        try {
          final T result = task.call();

          onReply(result);
        } catch (RuntimeException e) {
          throw new RuntimeException(e);
        } catch (Exception e) {
          onReply(defaultResult);
        }
      }
    });
  }

  @NonNull public Builder<T> newBuilder() {
    return new Builder<>(this);
  }

  private void onReply(@NonNull final T result) {
    handler.post(new Runnable() {
      @Override public void run() {
        reply.ready(result);
      }
    });
  }

  public static final class Builder<T> {

    final AsyncResult<T> EMPTY_REPLY = new AsyncResult<T>() {
      @Override public void ready(@NonNull T result) {
        // NO-OP
      }
    };

    final Callable<T> EMPTY_TASK = new Callable<T>() {
      @Override public T call() throws Exception {
        return defaultResult;
      }
    };

    ExecutorService executor;
    Handler handler;
    Callable<T> task;
    AsyncResult<T> reply;
    T defaultResult;

    public Builder(@NonNull T nextDefaultResult) {
      executor = Executors.newSingleThreadExecutor();
      handler = new Handler(Looper.getMainLooper());
      defaultResult = nextDefaultResult;
      task = EMPTY_TASK;
      reply = EMPTY_REPLY;
    }

    Builder(AsyncPipeline<T> current) {
      executor = current.executor;
      handler = current.handler;
      task = current.task;
      reply = current.reply;
      defaultResult = current.defaultResult;
    }

    public Builder<T> defaultResult(@NonNull T defaultResult) {
      this.defaultResult = defaultResult;
      return this;
    }

    public Builder<T> executor(@NonNull ExecutorService executor) {
      this.executor = executor;
      return this;
    }

    public Builder<T> handler(@NonNull Handler handler) {
      this.handler = handler;
      return this;
    }

    public Builder<T> reply(@NonNull AsyncResult<T> reply) {
      this.reply = reply;
      return this;
    }

    public Builder<T> task(@NonNull Callable<T> task) {
      this.task = task;
      return this;
    }

    public AsyncPipeline<T> build() {
      return new AsyncPipeline<>(this);
    }
  }
}
