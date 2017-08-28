package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;

/**
 * Created by alex
 */

public interface Storage<T> {
  void put(@NonNull String key, @Nullable T value);

  @NonNull T get(@NonNull String key);

  void delete(@NonNull String key);

  boolean contains(@NonNull String key);

  void clear();

  @NonNull List<T> all();
}
