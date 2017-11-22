package rm.com.disturb.data.signal;

import android.support.annotation.NonNull;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.TelegramCommand;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.inject.qualifier.Update;
import rm.com.disturb.utils.Formats;

/**
 * Created by alex
 */

@Singleton //
public final class CallMissedRule implements Rule<MessageSignal> {

  private final TelegramCommand<Optional<String>> update;
  private final Storage<MessageSignal> signalStorage;

  @Inject CallMissedRule(@NonNull @Update TelegramCommand<Optional<String>> update,
      @NonNull Storage<MessageSignal> signalStorage) {
    this.update = update;
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(MessageSignals.CALL_MISSED);
  }

  @Override public void apply(@NonNull MessageSignal item) {
    final MessageSignal signal = signalStorage.get(item.key()).orElse(MessageSignal.EMPTY_MESSAGE);

    if (signal.key().equals(MessageSignals.EMPTY) && !signal.type()
        .equals(MessageSignals.CALL_RINGING)) {
      return;
    }

    signalStorage.delete(item.key());

    final TelegramParams params = new TelegramParams.Builder().messageId(signal.remoteKey())
        .text(Formats.callMissedOf(signal.sender()))
        .build();

    update.send(params).subscribe();
  }
}
