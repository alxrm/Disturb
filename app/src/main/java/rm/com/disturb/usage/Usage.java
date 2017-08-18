package rm.com.disturb.usage;

import android.support.annotation.NonNull;

public interface Usage<T> {
  boolean shouldUse(@NonNull T item);
  void use(@NonNull T item);
}
