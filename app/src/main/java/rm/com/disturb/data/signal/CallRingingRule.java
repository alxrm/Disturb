package rm.com.disturb.data.signal;

import android.content.Context;
import android.support.annotation.NonNull;
import rm.com.disturb.data.async.Reply;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.inject.qualifier.Notify;
import rm.com.disturb.utils.Formats;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

public final class CallRingingRule implements Rule<MessageSignal> {

  private final @NonNull Context context;
  private final @NonNull Storage<MessageSignal> signalStorage;
  private final @NonNull ContactBook contactBook;
  private final @NonNull Command<String> notify;

  public CallRingingRule(@NonNull Context context, @NonNull Storage<MessageSignal> signalStorage,
      @NonNull ContactBook contactBook, @NonNull @Notify Command<String> notify) {
    this.context = context.getApplicationContext();
    this.signalStorage = signalStorage;
    this.contactBook = contactBook;
    this.notify = notify;
  }

  @Override public boolean shouldFollow(@NonNull MessageSignal item) {
    return item.type().equals(Signals.CALL_RINGING);
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
    final String message = Formats.callOf(from);

    notify.send(TelegramParams.ofMessage(message)).forget();
  }

  private void notifyWithContactName(@NonNull final String number) {
    contactBook.findName(number).whenReady(new Reply<String>() {
      @Override public void ready(@NonNull String contactName) {
        notifyCall(Formats.contactNameOf(contactName, number));
      }
    });
  }
}
