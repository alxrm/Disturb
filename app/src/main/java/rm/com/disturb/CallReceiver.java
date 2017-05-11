package rm.com.disturb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import java.text.MessageFormat;
import javax.inject.Inject;

/**
 * Created by alex
 */
public final class CallReceiver extends BroadcastReceiver {

  @Inject Notifier notifier;

  @Override public void onReceive(Context context, Intent intent) {
    ((DisturbApplication) context.getApplicationContext()).injector().inject(this);

    if (!Intents.isValid(intent, TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
      return;
    }

    final String state = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
    final String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

    if (TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(state)) {
      notifier.notify(MessageFormat.format("Number: {0}", number));
    }
  }
}