package rm.com.disturb.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import javax.inject.Inject;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.RuleSet;
import rm.com.disturb.data.signal.Signals;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.utils.Intents;

import static android.telephony.TelephonyManager.EXTRA_INCOMING_NUMBER;
import static android.telephony.TelephonyManager.EXTRA_STATE;
import static android.telephony.TelephonyManager.EXTRA_STATE_IDLE;

/**
 * Created by alex
 */
public final class CallReceiver extends BroadcastReceiver {

  @Inject RuleSet<MessageSignal> signalRuleSet;
  @Inject Storage<MessageSignal> signalStorage;

  @Override public void onReceive(Context context, Intent intent) {
    if (!Intents.matches(intent, TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
      return;
    }

    ((DisturbApplication) context.getApplicationContext()).injector().inject(this);

    final String state = intent.getExtras().getString(EXTRA_STATE, EXTRA_STATE_IDLE);
    final String number = intent.getExtras().getString(EXTRA_INCOMING_NUMBER, "Unknown number");

    if (!isRinging(state)) {
      return;
    }

    signalRuleSet.apply(new MessageSignal.Builder() //
        .phone(number) //
        .type(nextSignalTypeForState(signalStorage.get(number), state)) //
        .build());
  }

  @NonNull
  private String nextSignalTypeForState(@NonNull MessageSignal current, @NonNull String state) {
    return Signals.CALL_RINGING;
  }

  private boolean isRinging(@NonNull String state) {
    return TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(state);
  }
}