package rm.com.disturb.data.async;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public interface Reply<T> {
  void ready(@NonNull T result);
}