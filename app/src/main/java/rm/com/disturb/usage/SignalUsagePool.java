package rm.com.disturb.usage;

import android.support.annotation.NonNull;
import java.util.List;

public final class SignalUsagePool implements UsagePool<MessageSignal> {

  private final List<Usage<MessageSignal>> usages;

  public SignalUsagePool(@NonNull List<Usage<MessageSignal>> usages) {
    this.usages = usages;
  }

  @NonNull @Override public List<Usage<MessageSignal>> usages() {
    return usages;
  }

  @Override public void use(@NonNull MessageSignal nextItem) {
    // how?
    // type A -> notifySms() -> SmsUsage -> Notify -> null key -> not pushing
    // type !A -> notifyCall() -> CallUsage -> Notify -> key -> push -> Erase, Update
  }
}
