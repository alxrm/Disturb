package rm.com.disturb.data.signal;

import android.support.annotation.NonNull;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.TelegramCommand;
import rm.com.disturb.inject.qualifier.Erase;
import rm.com.disturb.inject.qualifier.Update;

/**
 * Created by alex
 */

public final class CallAnsweredRule implements Rule<MessageSignal> {

  private final TelegramCommand<String> update;
  private final TelegramCommand<Boolean> erase;
  private final Storage<MessageSignal> signalStorage;

  public CallAnsweredRule(@NonNull @Update TelegramCommand<String> update,
      @NonNull @Erase TelegramCommand<Boolean> erase, @NonNull Storage<MessageSignal> signalStorage) {
    this.update = update;
    this.erase = erase;
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(Signals.CALL_ANSWERED);
  }

  @Override public void apply(@NonNull final MessageSignal item) {
    final MessageSignal signal = signalStorage.get(item.key());

    if (signal.key().equals(Signals.EMPTY) && !signal.type().equals(Signals.CALL_RINGING)) {
      return;
    }

    signalStorage.put(signal.key(), signal.newBuilder().type(Signals.CALL_ANSWERED).build());
  }
}
