package rm.com.disturb.command;

import android.support.annotation.NonNull;
import rm.com.disturb.async.AsyncResult;

/**
 * Created by alex
 */

public interface Erase {
  boolean deleteBlocking(@NonNull String messageId);
  void deleteAsync(@NonNull String messageId);
  void deleteAsync(@NonNull String messageId, @NonNull AsyncResult<Boolean> result);
}
