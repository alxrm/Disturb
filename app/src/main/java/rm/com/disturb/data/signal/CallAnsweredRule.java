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

public final class CallAnsweredRule implements Rule<MessageSignal> {

  private final Command<String> update;
  private final Command<Boolean> erase;
  private final Storage<MessageSignal> signalStorage;

  public CallAnsweredRule(@NonNull @Update Command<String> update,
      @NonNull @Erase Command<Boolean> erase, @NonNull Storage<MessageSignal> signalStorage) {
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

    final TelegramParams params = new TelegramParams.Builder().messageId(signal.remoteKey())
        .text(Formats.callAnsweredOf(signal.sender()))
        .build();

    signalStorage.put(signal.key(), signal.newBuilder().type(Signals.CALL_ANSWERED).build());

    // TODO later implement deletion by preference
    //update.send(params).whenReady(new Reply<String>() {
    //  @Override public void ready(@NonNull String result) {
    //  }
    //});
  }
}
