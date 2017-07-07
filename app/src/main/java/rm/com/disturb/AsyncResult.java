package rm.com.disturb;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

interface AsyncResult<T> {
  void ready(@NonNull T result);
}