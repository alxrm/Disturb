package rm.com.disturb.data.signal;

import android.content.Context;
import android.support.annotation.NonNull;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.inject.qualifier.Erase;
import rm.com.disturb.inject.qualifier.Update;

/**
 * Created by alex
 */

public final class CallFinishedRule implements Rule<MessageSignal> {

  private final @NonNull Context context;
  private final @NonNull ContactBook contactBook;
  private final @NonNull Command<String> update;
  private final @NonNull Command<Boolean> erase;
  private final @NonNull Storage<MessageSignal> signalStorage;

  public CallFinishedRule(@NonNull Context context, @NonNull ContactBook contactBook,
      @NonNull @Update Command<String> update, @NonNull @Erase Command<Boolean> erase,
      @NonNull Storage<MessageSignal> signalStorage) {
    this.context = context;
    this.contactBook = contactBook;
    this.update = update;
    this.erase = erase;
    this.signalStorage = signalStorage;
  }

  @Override public boolean shouldFollow(@NonNull MessageSignal item) {
    return item.type().equals(Signals.CALL_FINISHED);
  }

  @Override public void follow(@NonNull MessageSignal item) {

  }
}
