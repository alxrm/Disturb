package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;
import rm.com.disturb.utils.Lists;

/**
 * Created by alex
 */

public interface Storage<T> {
  void put(@NonNull String key, @Nullable T value);

  @Nullable T get(@NonNull String key);

  boolean contains(@NonNull String key);

  @NonNull List<T> all();

  @NonNull List<T> some(@NonNull Lists.Predicate<T> clause);
}
