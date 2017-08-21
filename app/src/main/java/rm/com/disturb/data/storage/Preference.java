package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public interface Preference<T> {
  @NonNull T get();

  void set(@NonNull T value);

  boolean isSet();

  void delete();
}
