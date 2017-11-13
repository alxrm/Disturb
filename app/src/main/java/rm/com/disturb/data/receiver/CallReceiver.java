package rm.com.disturb.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;
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
import static android.telephony.TelephonyManager.EXTRA_STATE_OFFHOOK;
import static android.telephony.TelephonyManager.EXTRA_STATE_RINGING;

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
    final String signalType = nextSignalTypeForState(signalStorage.get(number), state);

    Log.e("DBG", "Signal type: " + signalType);

    signalRuleSet.apply(new MessageSignal.Builder() //
        .phone(number) //
        .type(signalType) //
        .build());
  }

  @NonNull
  private String nextSignalTypeForState(@NonNull MessageSignal current, @NonNull String state) {
    final String prevType = current.type();

    if (prevType.equals(Signals.EMPTY) && state.equals(EXTRA_STATE_RINGING)) {
      return Signals.CALL_RINGING;
    }

    if (prevType.equals(Signals.CALL_RINGING) && state.equals(EXTRA_STATE_OFFHOOK)) {
      return Signals.CALL_ANSWERED;
    }

    if (prevType.equals(Signals.CALL_RINGING) && state.equals(EXTRA_STATE_IDLE)) {
      return Signals.CALL_MISSED;
    }

    if (prevType.equals(Signals.CALL_ANSWERED) && state.equals(EXTRA_STATE_IDLE)) {
      return Signals.CALL_FINISHED;
    }

    return Signals.EMPTY;
  }
}