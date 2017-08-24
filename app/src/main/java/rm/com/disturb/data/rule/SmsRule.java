package rm.com.disturb.data.rule;

import android.content.Context;
import android.support.annotation.NonNull;
import rm.com.disturb.data.async.AsyncResult;
import rm.com.disturb.data.command.Notify;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.utils.Formats;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

public final class SmsRule implements Rule<MessageSignal> {

  private final @NonNull ContactBook contactBook;
  private final @NonNull Context context;
  private final @NonNull Notify notify;

  public SmsRule(@NonNull ContactBook contactBook, @NonNull Context context,
      @NonNull Notify notify) {
    this.contactBook = contactBook;
    this.context = context;
    this.notify = notify;
  }

  @Override public boolean shouldFollow(@NonNull MessageSignal item) {
    return item.type().equals(Signals.SMS_RECEIVED);
  }

  @Override public void follow(@NonNull MessageSignal item) {
    final String number = item.key();
    final String messageText = item.data();

    if (Permissions.isReadContactsPermissionGranted(context)) {
      notifyWithContactName(number, messageText);
    } else {
      notifySms(number, messageText);
    }
  }

  private void notifySms(@NonNull String from, @NonNull String text) {
    notify.send(Formats.smsOf(from, text));
  }

  private void notifyWithContactName(@NonNull final String number,
      @NonNull final String messageText) {
    contactBook.findNameAsync(number, new AsyncResult<String>() {
      @Override public void ready(@NonNull String contactName) {
        final String from = Formats.contactNameOf(contactName, number);

        notifySms(from, messageText);
      }
    });
  }
}
