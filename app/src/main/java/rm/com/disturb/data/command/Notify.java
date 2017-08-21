package rm.com.disturb.data.command;

import android.support.annotation.NonNull;
import rm.com.disturb.data.async.AsyncResult;

/**
 * Created by alex
 */

public interface Notify {
  void send(@NonNull String message);

  @NonNull String sendBlocking(@NonNull String message);

  void sendAsync(@NonNull String message, @NonNull AsyncResult<String> result);
}
