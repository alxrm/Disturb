package rm.com.disturb.pool;

import android.support.annotation.NonNull;
import rm.com.disturb.telegram.Notify;

/**
 * Created by alex
 */

public abstract class MessageSignalUsage implements Usage<MessageSignal> {

  private final @NonNull Notify notify;

  public MessageSignalUsage(@NonNull Notify notify) {
    this.notify = notify;
  }
}
