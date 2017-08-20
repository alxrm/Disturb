package rm.com.disturb.command;

import android.support.annotation.NonNull;
import rm.com.disturb.async.AsyncResult;

/**
 * Created by alex
 */

public interface Auth {
  boolean authorizeBlocking(@NonNull String chatId);
  void authorizeAsync(@NonNull String chatId, @NonNull AsyncResult<Boolean> asyncResult);
}
