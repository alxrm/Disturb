package rm.com.disturb.pool;

import android.support.annotation.NonNull;
import java.util.List;

public final class SignalUsagePool implements UsagePool<MessageSignal> {

  public SignalUsagePool(@NonNull List<MessageSignalUsage> usages) {

  }

  @NonNull @Override public List<Usage<MessageSignal>> usages() {
    return null;
  }

  @Override public void use(@NonNull MessageSignal nextItem) {
    // how?
    // type A -> notifySms() -> SmsUsage -> Notify -> null key -> not pushing
    // type !A -> notifyCall() -> CallUsage -> Notify -> key -> push -> Erase, Update
  }
}
