package rm.com.disturb.data.resource;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import rm.com.disturb.data.async.PendingResult;

/**
 * Created by alex
 */

public final class AssetResource implements Resource<Typeface, String> {

  private final PendingResult<Typeface> result;

  public AssetResource(@NonNull ExecutorService executor, @NonNull Handler handler) {
    this.result = new PendingResult.Builder<>(Typeface.DEFAULT) //
        .executor(executor) //
        .handler(handler) //
        .build();
  }

  @Override public PendingResult<Typeface> load(@NonNull Context context, @NonNull String path) {
    return result.newBuilder().from(asCallable(context, path)).build();
  }

  @NonNull private Callable<Typeface> asCallable(@NonNull Context context, @NonNull String path) {
    return new Callable<Typeface>() {
      @Override public Typeface call() throws Exception {
        return Typeface.DEFAULT;
      }
    };
  }
}