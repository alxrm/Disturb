package rm.com.disturb.data.rule;

import android.content.Context;
import android.support.annotation.NonNull;
import rm.com.disturb.data.async.AsyncResult;
import rm.com.disturb.data.command.Erase;
import rm.com.disturb.data.command.Notify;
import rm.com.disturb.data.command.Update;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.utils.Formats;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

public final class CallRule implements Rule<MessageSignal> {

  private final @NonNull Context context;
  private final @NonNull Storage<MessageSignal> signalStorage;
  private final @NonNull ContactBook contactBook;
  private final @NonNull Notify notify;
  private final @NonNull Update update;
  private final @NonNull Erase erase;

  public CallRule(@NonNull Context context, @NonNull Storage<MessageSignal> signalStorage,
      @NonNull ContactBook contactBook, @NonNull Notify notify, @NonNull Update update,
      @NonNull Erase erase) {
    this.context = context.getApplicationContext();
    this.signalStorage = signalStorage;
    this.contactBook = contactBook;
    this.notify = notify;
    this.update = update;
    this.erase = erase;
  }

  @Override public boolean shouldFollow(@NonNull MessageSignal item) {
    return !item.type().equals(Signals.SMS_RECEIVED);
  }

  @Override public void follow(@NonNull MessageSignal item) {
    final String number = item.key();

    if (Permissions.isReadContactsPermissionGranted(context)) {
      notifyWithContactName(number);
    } else {
      notifyCall(number);
    }
  }

  private void notifyCall(@NonNull String from) {
    notify.send(Formats.callOf(from));
  }

  private void notifyWithContactName(@NonNull final String number) {
    contactBook.findNameAsync(number, new AsyncResult<String>() {
      @Override public void ready(@NonNull String contactName) {
        notifyCall(Formats.contactNameOf(contactName, number));
      }
    });
  }
}
