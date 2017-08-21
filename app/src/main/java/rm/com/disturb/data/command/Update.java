package rm.com.disturb.data.command;

import android.support.annotation.NonNull;
import rm.com.disturb.data.async.AsyncResult;

/**
 * Created by alex
 */

public interface Update {
  void sendAsync(@NonNull String messageId, @NonNull String message);

  void sendAsync(@NonNull String messageId, @NonNull String message,
      @NonNull AsyncResult<String> result);

  @NonNull String sendBlocking(@NonNull String messageId, @NonNull String message);
}
