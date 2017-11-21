package rm.com.disturb.data.signal;

import android.content.Context;
import android.support.annotation.NonNull;
import rm.com.disturb.data.resource.Resource;
import rm.com.disturb.data.telegram.command.TelegramCommand;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.utils.Formats;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

public final class SmsRule implements Rule<MessageSignal> {

  private final Resource<String, String> contactResource;
  private final Context context;
  private final TelegramCommand<String> notify;

  public SmsRule(@NonNull Resource<String, String> contactResource, @NonNull Context context,
      @NonNull TelegramCommand<String> notify) {
    this.contactResource = contactResource;
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

  private void notifyWithContactName(@NonNull String number, @NonNull String messageText) {
    contactResource.load(context, number).whenReady(contactName -> {
      final String from = Formats.contactNameOf(contactName, number);

      notifySms(from, messageText);
    });
  }
}
