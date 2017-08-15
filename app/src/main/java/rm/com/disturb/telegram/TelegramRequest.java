package rm.com.disturb.telegram;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by alex
 */

public final class TelegramRequest {

  @NonNull public <T> Reply<T> execute(@NonNull Call<T> call) {
    final CountDownLatch latch = new CountDownLatch(1);
    final AtomicReference<T> result = new AtomicReference<>();
    final AtomicReference<Throwable> error = new AtomicReference<>();

    call.enqueue(new Callback<T>() {
      @Override public void onResponse(Call<T> call, Response<T> response) {
        final T resultEntity = response.body();

        if (response.isSuccessful()) {
          result.set(resultEntity);
        }

        latch.countDown();
      }

      @Override public void onFailure(Call<T> call, Throwable t) {
        error.set(t);
        latch.countDown();
      }
    });

    waitSilently(latch);

    return replyOf(result.get(), error.get());
  }

  private void waitSilently(@NonNull CountDownLatch latch) {
    try {
      latch.await(3, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      // ignore
    }
  }

  @NonNull
  private static <T> Reply<T> replyOf(@Nullable final T result, @Nullable final Throwable error) {
    return new Reply<T>() {
      @Nullable @Override public T data() {
        return result;
      }

      @Nullable @Override public Throwable error() {
        return error;
      }

      @Override public boolean isSuccessful() {
        return error == null && result != null;
      }
    };
  }

  interface Reply<T> {
    @Nullable T data();

    @Nullable Throwable error();

    boolean isSuccessful();
  }
}
