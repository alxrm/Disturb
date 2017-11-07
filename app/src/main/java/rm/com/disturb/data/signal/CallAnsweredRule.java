package rm.com.disturb.data.signal;

import android.support.annotation.NonNull;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.inject.qualifier.Erase;
import rm.com.disturb.inject.qualifier.Update;

/**
 * Created by alex
 */

public final class CallAnsweredRule implements Rule<MessageSignal> {

  private final ContactBook contactBook;
  private final Command<String> update;
  private final Command<Boolean> erase;
  private final Storage<MessageSignal> signalStorage;

  public CallAnsweredRule(@NonNull ContactBook contactBook, @NonNull @Update Command<String> update,
      @NonNull @Erase Command<Boolean> erase, @NonNull Storage<MessageSignal> signalStorage) {
    this.contactBook = contactBook;
    this.update = update;
    this.erase = erase;
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(Signals.CALL_ANSWERED);
  }

  @Override public void apply(@NonNull MessageSignal item) {

  }
}
