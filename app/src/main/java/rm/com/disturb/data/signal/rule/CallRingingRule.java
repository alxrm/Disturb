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
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.TelegramCommand;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.inject.qualifier.Notify;
import rm.com.disturb.utils.Formats;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

@Singleton //
public final class CallRingingRule implements Rule<MessageSignal> {

  private final Context context;
  private final Storage<MessageSignal> signalStorage;
  private final Resource<String, String> contactResource;
  private final TelegramCommand<Optional<String>> notify;

  @Inject CallRingingRule(@NonNull Application application,
      @NonNull Storage<MessageSignal> signalStorage,
      @NonNull Resource<String, String> contactResource,
      @NonNull @Notify TelegramCommand<Optional<String>> notify) {
    this.context = application.getApplicationContext();
    this.signalStorage = signalStorage;
    this.contactResource = contactResource;
    this.notify = notify;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return item.type().equals(MessageSignals.CALL_RINGING);
  }

  @Override public void apply(@NonNull MessageSignal item) {
    final String number = item.key();

    if (Permissions.isReadContactsPermissionGranted(context)) {
      notifyWithContactName(number, item);
    } else {
      notifyCall(number, item);
    }
  }

  private void notifyCall(@NonNull String from, @NonNull MessageSignal item) {
    final String message = Formats.callRingingOf(from);

    notify.send(TelegramParams.ofMessage(message))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .subscribe(messageId -> {
          final MessageSignal full = item.newBuilder().remoteKey(messageId).sender(from).build();

          signalStorage.put(item.key(), full);
        });
  }

  private void notifyWithContactName(@NonNull String number, @NonNull MessageSignal item) {
    contactResource //
        .load(context, number) //
        .map(contact -> contact.orElse(""))
        .doOnSuccess(contact -> notifyCall(Formats.contactNameOf(contact, number), item))
        .subscribe();
  }
}
