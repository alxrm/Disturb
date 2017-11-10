package rm.com.disturb.data.signal;

import android.content.Context;
import android.support.annotation.NonNull;
import rm.com.disturb.data.async.Reply;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.utils.Formats;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

public final class SmsRule implements Rule<MessageSignal> {

  private final ContactBook contactBook;
  private final Context context;
  private final Command<String> notify;

  public SmsRule(@NonNull ContactBook contactBook, @NonNull Context context,
      @NonNull Command<String> notify) {
    this.contactBook = contactBook;
    this.context = context;
    this.notify = notify;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(Signals.SMS_RECEIVED);
  }

  @Override public void apply(@NonNull MessageSignal item) {
    final String number = item.key();
    final String messageText = item.data();

    if (Permissions.isReadContactsPermissionGranted(context)) {
      notifyWithContactName(number, messageText);
    } else {
      notifySms(number, messageText);
    }
  }

  private void notifySms(@NonNull String from, @NonNull String text) {
    notify.send(TelegramParams.ofMessage(Formats.smsOf(from, text))).completeSilently();
  }

  private void notifyWithContactName(@NonNull final String number,
      @NonNull final String messageText) {
    contactBook.findName(number).whenReady(new Reply<String>() {
      @Override public void ready(@NonNull String contactName) {
        final String from = Formats.contactNameOf(contactName, number);

        notifySms(from, messageText);
      }
    });
  }
}
