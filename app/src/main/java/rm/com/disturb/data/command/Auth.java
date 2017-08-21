package rm.com.disturb.data.command;

import android.support.annotation.NonNull;
import rm.com.disturb.data.async.AsyncResult;

/**
 * Created by alex
 */

public interface Auth {
  boolean authorizeBlocking(@NonNull String chatId);
  void authorizeAsync(@NonNull String chatId, @NonNull AsyncResult<Boolean> asyncResult);
}
