package rm.com.disturb.data.command;

import android.support.annotation.NonNull;
import rm.com.disturb.data.async.AsyncResult;

/**
 * Created by alex
 */

public interface Erase {
  boolean deleteBlocking(@NonNull String messageId);
  void deleteAsync(@NonNull String messageId);
  void deleteAsync(@NonNull String messageId, @NonNull AsyncResult<Boolean> result);
}
