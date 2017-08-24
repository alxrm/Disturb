package rm.com.disturb.data.rule;

import android.support.annotation.NonNull;
import rm.com.disturb.data.command.Erase;
import rm.com.disturb.data.command.Notify;
import rm.com.disturb.data.command.Update;
import rm.com.disturb.data.storage.Storage;

/**
 * Created by alex
 */

public final class CallRule implements Rule<MessageSignal> {

  private final @NonNull Storage<MessageSignal> signalStorage;
  private final @NonNull Notify notify;
  private final @NonNull Update update;
  private final @NonNull Erase erase;

  public CallRule(@NonNull Storage<MessageSignal> signalStorage, @NonNull Notify notify,
      @NonNull Update update, @NonNull Erase erase) {
    this.signalStorage = signalStorage;
    this.notify = notify;
    this.update = update;
    this.erase = erase;
  }

  @Override public boolean shouldFollow(@NonNull MessageSignal item) {
    return !item.type().equals(Signals.SMS_RECEIVED);
  }

  @Override public void follow(@NonNull MessageSignal item) {

  }
}
