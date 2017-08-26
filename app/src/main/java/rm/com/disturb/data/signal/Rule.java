package rm.com.disturb.data.signal;

import android.support.annotation.NonNull;

public interface Rule<T> {
  boolean shouldFollow(@NonNull T item);
  void follow(@NonNull T item);
}
