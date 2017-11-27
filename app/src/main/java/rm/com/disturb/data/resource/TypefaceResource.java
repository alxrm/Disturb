package rm.com.disturb.data.resource;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.HashMap;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import rm.com.disturb.utils.Preconditions;

/**
 * Created by alex
 */

@Singleton //
public final class TypefaceResource implements Resource<Typeface, String> {

  @Inject TypefaceResource() {
  }

  private final HashMap<String, Typeface> typefaceCache = new HashMap<>(5);

  @NonNull @Override
  public Single<Optional<Typeface>> load(@NonNull Context context, @NonNull String path) {
    return Single //
        .fromCallable(() -> {
          if (typefaceCache.containsKey(path)) {
            return Optional.ofNullable(typefaceCache.get(path));
          }

          final Typeface typeface = Typeface.createFromAsset(context.getAssets(), path);
          Preconditions.checkNotNull(typeface, "Could not load typeface from this path: " + path);

          typefaceCache.put(path, typeface);

          return Optional.of(typeface);
        }) //
        .observeOn(AndroidSchedulers.mainThread()) //
        .subscribeOn(Schedulers.single());
  }
}