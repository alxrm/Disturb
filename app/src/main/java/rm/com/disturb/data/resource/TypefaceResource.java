package rm.com.disturb.data.resource;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import rm.com.disturb.data.async.PendingResult;
import rm.com.disturb.utils.Preconditions;

/**
 * Created by alex
 */

public final class TypefaceResource implements Resource<Typeface, String> {

  private final HashMap<String, Typeface> typefaceCache = new HashMap<>(5);
  private final PendingResult<Typeface> result;

  public TypefaceResource(@NonNull ExecutorService executor, @NonNull Handler handler) {
    this.result = new PendingResult.Builder<>(Typeface.DEFAULT) //
        .executor(executor) //
        .handler(handler) //
        .build();
  }

  @Override public PendingResult<Typeface> load(@NonNull Context context, @NonNull String path) {
    return result.newBuilder().from(asCallable(context, path)).build();
  }

  @NonNull
  private Callable<Typeface> asCallable(@NonNull final Context context, @NonNull final String path) {
    return () -> {
      if (typefaceCache.containsKey(path)) {
        return typefaceCache.get(path);
      }

      final Typeface typeface = Typeface.createFromAsset(context.getAssets(), path);
      Preconditions.checkNotNull(typeface, "Could not load typeface from this path: " + path);

      typefaceCache.put(path, typeface);

      return typeface;
    };
  }
}