package rm.com.disturb.data.signal.rule;

import android.support.annotation.NonNull;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.MessageSignals;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.TelegramCommand;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.inject.qualifier.Erase;
import rm.com.disturb.inject.qualifier.Finished;
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
  private final Provider<String> finishedCallBehavior;
  private final Storage<MessageSignal> signalStorage;

  @Inject CallFinishedRule(@NonNull @Update TelegramCommand<Optional<String>> update,
      @NonNull @Erase TelegramCommand<Boolean> erase,
      @NonNull @Finished Provider<String> finishedCallBehavior,
      @NonNull Storage<MessageSignal> signalStorage) {
    this.update = update;
    this.erase = erase;
    this.finishedCallBehavior = finishedCallBehavior;
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal signal) {
    return signal.type().equals(MessageSignals.CALL_FINISHED);
  }

  @Override public void apply(@NonNull MessageSignal signal) {
    final MessageSignal saved = signalStorage.get(signal.key()).orElse(EMPTY_MESSAGE);

    if (saved.key().equals(EMPTY) && !saved.type().equals(CALL_ANSWERED)) {
      return;
    }

    signalStorage.delete(signal.key());

    final TelegramParams params = new TelegramParams.Builder().messageId(saved.remoteKey())
        .text(Formats.callFinishedOf(saved.sender()))
        .build();

    if (finishedCallBehavior.get().startsWith("Edit")) {
      update.send(params).subscribe();
    } else {
      erase.send(TelegramParams.ofMessageId(saved.remoteKey())).subscribe();
    }
  }
}
