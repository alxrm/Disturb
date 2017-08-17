package rm.com.disturb.pool;

import android.support.annotation.NonNull;

public interface Usage<T> {
  void use(@NonNull T item);
}
