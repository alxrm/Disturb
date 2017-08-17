package rm.com.disturb.pool;

import android.support.annotation.NonNull;

public interface Signal<T> {
  @NonNull String type();

  @NonNull T data();

  long time();

  @NonNull String key();
}
