package rm.com.disturb.data.signal.rule;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.MessageSignals;
import rm.com.disturb.data.storage.Storage;

import static rm.com.disturb.data.signal.MessageSignal.EMPTY_MESSAGE;
import static rm.com.disturb.data.signal.MessageSignals.CALL_RINGING;
import static rm.com.disturb.data.signal.MessageSignals.EMPTY;

/**
 * Created by alex
 */

@Singleton //
public final class CallAnsweredRule implements Rule<MessageSignal> {

  private final Storage<MessageSignal> signalStorage;

  @Inject CallAnsweredRule(@NonNull Storage<MessageSignal> signalStorage) {
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(MessageSignals.CALL_ANSWERED);
  }

  @Override public void apply(@NonNull final MessageSignal item) {
    final MessageSignal signal = signalStorage.get(item.key()).orElse(EMPTY_MESSAGE);

    if (signal.key().equals(EMPTY) && !signal.type().equals(CALL_RINGING)) {
      return;
    }

    signalStorage.put(signal.key(), signal.newBuilder().type(MessageSignals.CALL_ANSWERED).build());
  }
}
