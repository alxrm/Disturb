package rm.com.disturb.data.signal;

import android.content.Context;
import android.support.annotation.NonNull;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.inject.qualifier.Update;

/**
 * Created by alex
 */

public final class CallMissedRule implements Rule<MessageSignal> {

  private final Context context;
  private final ContactBook contactBook;
  private final Command<String> update;
  private final Storage<MessageSignal> signalStorage;

  public CallMissedRule(@NonNull Context context, @NonNull ContactBook contactBook,
      @NonNull @Update Command<String> update, @NonNull Storage<MessageSignal> signalStorage) {
    this.context = context;
    this.contactBook = contactBook;
    this.update = update;
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(Signals.CALL_MISSED);
  }

  @Override public void apply(@NonNull MessageSignal item) {

  }
}
