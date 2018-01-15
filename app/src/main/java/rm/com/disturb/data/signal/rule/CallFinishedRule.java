package rm.com.disturb.data.signal.rule;

import android.support.annotation.NonNull;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.MessageSignals;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.TelegramCommand;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.inject.qualifier.Erase;
import rm.com.disturb.inject.qualifier.Update;
import rm.com.disturb.utils.Formats;

import static rm.com.disturb.data.signal.MessageSignal.EMPTY_MESSAGE;
import static rm.com.disturb.data.signal.MessageSignals.CALL_ANSWERED;
import static rm.com.disturb.data.signal.MessageSignals.EMPTY;

/**
 * Created by alex
 */

@Singleton //
public final class CallFinishedRule implements Rule<MessageSignal> {

  private final TelegramCommand<Optional<String>> update;
  private final TelegramCommand<Boolean> erase;
  private final Storage<MessageSignal> signalStorage;

  @Inject CallFinishedRule(@NonNull @Update TelegramCommand<Optional<String>> update,
      @NonNull @Erase TelegramCommand<Boolean> erase,
      @NonNull Storage<MessageSignal> signalStorage) {
    this.update = update;
    this.erase = erase;
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(MessageSignals.CALL_FINISHED);
  }

  @Override public void apply(@NonNull MessageSignal item) {
    final MessageSignal signal = signalStorage.get(item.key()).orElse(EMPTY_MESSAGE);

    if (signal.key().equals(EMPTY) && !signal.type().equals(CALL_ANSWERED)) {
      return;
    }

    signalStorage.delete(item.key());

    final TelegramParams params = new TelegramParams.Builder().messageId(signal.remoteKey())
        .text(Formats.callFinishedOf(signal.sender()))
        .build();

    // TODO later implement deletion by preference
    update.send(params).subscribe();
  }
}
