package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import rm.com.disturb.data.async.PendingResult;

/**
 * Created by alex
 */

public interface TelegramCommand<T> {
  @NonNull PendingResult<T> send(@NonNull TelegramParams params);
}
