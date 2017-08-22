package rm.com.disturb.data.rule;

import android.support.annotation.NonNull;
import java.util.List;

public final class SignalRulePool implements RulePool<MessageSignal> {

  private final List<Rule<MessageSignal>> usages;

  public SignalRulePool(@NonNull List<Rule<MessageSignal>> usages) {
    this.usages = usages;
  }

  @NonNull @Override public List<Rule<MessageSignal>> rules() {
    return usages;
  }

  @Override public void consume(@NonNull MessageSignal nextItem) {
    // how?
    // type A -> notifySms() -> SmsRule -> Notify -> null key -> not pushing
    // type !A -> notifyCall() -> CallRule -> Notify -> key -> push -> Erase, Update
  }
}
