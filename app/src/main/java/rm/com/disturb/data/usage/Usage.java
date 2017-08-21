package rm.com.disturb.data.usage;

import android.support.annotation.NonNull;

public interface Usage<T> {
  boolean shouldUse(@NonNull T item);
  void use(@NonNull T item);
}
