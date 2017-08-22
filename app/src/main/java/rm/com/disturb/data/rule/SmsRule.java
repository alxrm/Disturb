package rm.com.disturb.data.rule;

import android.support.annotation.NonNull;
import rm.com.disturb.data.command.Notify;

/**
 * Created by alex
 */

public final class SmsRule implements Rule<MessageSignal> {

  public SmsRule(@NonNull Notify notify) {
  }

  @Override public boolean shouldFollow(@NonNull MessageSignal item) {
    return false;
  }

  @Override public void follow(@NonNull MessageSignal item) {

  }
}
