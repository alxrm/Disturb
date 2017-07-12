package rm.com.disturb.async;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public interface AsyncResult<T> {
  void ready(@NonNull T result);
}