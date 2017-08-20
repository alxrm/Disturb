package rm.com.disturb.telegram;

import android.support.annotation.NonNull;
import rm.com.disturb.async.AsyncResult;

/**
 * Created by alex
 */

public final class TelegramErase implements Erase {
  @Override public boolean deleteBlocking(@NonNull String messageId) {
    return false;
  }

  @Override
  public void deleteAsync(@NonNull String messageId, @NonNull AsyncResult<Boolean> result) {

  }
}
