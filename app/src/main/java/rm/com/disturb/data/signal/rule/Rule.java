package rm.com.disturb.data.signal.rule;

import android.support.annotation.NonNull;

public interface Rule<T> {
  boolean shouldApply(@NonNull T item);
  void apply(@NonNull T item);
}
