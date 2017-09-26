package rm.com.disturb.data.async;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public interface Transform<T, R> {
  @NonNull R apply(@NonNull T input);
}
