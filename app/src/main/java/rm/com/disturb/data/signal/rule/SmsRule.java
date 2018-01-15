package rm.com.disturb.data.signal.rule;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import rm.com.disturb.data.resource.Resource;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.MessageSignals;
import rm.com.disturb.data.telegram.command.TelegramCommand;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.inject.qualifier.Notify;
import rm.com.disturb.utils.Formats;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

@Singleton //
public final class SmsRule implements Rule<MessageSignal> {

  private final Resource<String, String> contactResource;
  private final Context context;
  private final TelegramCommand<Optional<String>> notify;

  @Inject SmsRule(@NonNull Application application,
      @NonNull Resource<String, String> contactResource,
      @NonNull @Notify TelegramCommand<Optional<String>> notify) {
    this.context = application.getApplicationContext();
    this.contactResource = contactResource;
    this.notify = notify;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(MessageSignals.SMS_RECEIVED);
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
    notify.send(TelegramParams.ofMessage(Formats.smsOf(from, text))).subscribe();
  }

  private void notifyWithContactName(@NonNull String number, @NonNull String messageText) {
    contactResource.load(context, number)
        .map(contact -> contact.orElse(""))
        .doOnSuccess(contact -> notifySms(Formats.contactNameOf(contact, number), messageText))
        .subscribe();
  }
}
