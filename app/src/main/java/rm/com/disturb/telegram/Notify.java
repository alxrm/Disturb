package rm.com.disturb.telegram;

import android.support.annotation.NonNull;
import rm.com.disturb.async.AsyncResult;

/**
 * Created by alex
 */

public interface Notify {
  void send(@NonNull String message);

  @NonNull String sendBlocking(@NonNull String message);

  void sendAsync(@NonNull String message, @NonNull AsyncResult<String> result);
}
