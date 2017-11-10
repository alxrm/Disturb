package rm.com.disturb.data.signal;

import android.support.annotation.NonNull;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.inject.qualifier.Erase;
import rm.com.disturb.inject.qualifier.Update;
import rm.com.disturb.utils.Formats;

/**
 * Created by alex
 */

public final class CallFinishedRule implements Rule<MessageSignal> {

  private final Command<String> update;
  private final Command<Boolean> erase;
  private final Storage<MessageSignal> signalStorage;

  public CallFinishedRule(@NonNull @Update Command<String> update,
      @NonNull @Erase Command<Boolean> erase, @NonNull Storage<MessageSignal> signalStorage) {
    this.update = update;
    this.erase = erase;
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(Signals.CALL_FINISHED);
  }

  @Override public void apply(@NonNull final MessageSignal item) {
    final MessageSignal signal = signalStorage.get(item.key());

    if (signal.key().equals(Signals.EMPTY) && !signal.type().equals(Signals.CALL_ANSWERED)) {
      return;
    }

    signalStorage.delete(item.key());

    final TelegramParams params = new TelegramParams.Builder().messageId(signal.remoteKey())
        .text(Formats.callFinishedOf(signal.sender()))
        .build();

    // TODO later implement deletion by preference
    update.send(params).completeSilently();
  }
}
