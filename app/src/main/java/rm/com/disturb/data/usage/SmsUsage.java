package rm.com.disturb.data.usage;

import android.support.annotation.NonNull;
import rm.com.disturb.data.command.Notify;

/**
 * Created by alex
 */

public final class SmsUsage implements Usage<MessageSignal> {

  public SmsUsage(@NonNull Notify notify) {
  }

  @Override public boolean shouldUse(@NonNull MessageSignal item) {
    return false;
  }

  @Override public void use(@NonNull MessageSignal item) {

  }
}
