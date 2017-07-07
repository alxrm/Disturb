package rm.com.disturb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import javax.inject.Inject;

/**
 * Created by alex
 */
public final class CallReceiver extends BroadcastReceiver {

  @Inject Notifier notifier;
  @Inject ContactBook contactBook;

  @Override public void onReceive(Context context, Intent intent) {
    if (!Intents.matches(intent, TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
      return;
    }

    ((DisturbApplication) context.getApplicationContext()).injector().inject(this);

    final String state = intent.getExtras()
        .getString(TelephonyManager.EXTRA_STATE, TelephonyManager.EXTRA_STATE_IDLE);

    final String number =
        intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER, "Unknown number");

    if (!isRinging(state)) {
      return;
    }

    if (Permissions.isReadContactsPermissionGranted(context)) {
      notifyWithContactName(number);
    } else {
      notifyCall(number);
    }
  }

  private void notifyCall(@NonNull String from) {
    notifier.notify(Formats.callOf(from));
  }

  private void notifyWithContactName(@NonNull final String number) {
    contactBook.findNameAsync(number, new AsyncResult<String>() {
      @Override public void ready(@NonNull String contactName) {
        notifyCall(Formats.contactNameOf(contactName, number));
      }
    });
  }

  private boolean isRinging(@NonNull String state) {
    return TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(state);
  }
}