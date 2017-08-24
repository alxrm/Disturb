package rm.com.disturb.data.rule;

import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.List;

public final class SignalRuleSet implements RuleSet<MessageSignal> {

  private final List<Rule<MessageSignal>> rules;

  public SignalRuleSet(@NonNull List<Rule<MessageSignal>> rules) {
    this.rules = Collections.unmodifiableList(rules);
  }

  @NonNull @Override public List<Rule<MessageSignal>> rules() {
    return rules;
  }

  @Override public void consume(@NonNull MessageSignal nextItem) {
    for (int i = 0, size = rules.size(); i < size; i++) {
      final Rule<MessageSignal> currentRule = rules.get(i);

      if (currentRule.shouldFollow(nextItem)) {
        currentRule.follow(nextItem);
      }
    }

    // how?
    // type A -> notifySms() -> SmsRule -> Notify -> null key -> not pushing
    // type !A -> notifyCall() -> CallRule -> Notify -> key -> push -> Erase, Update
  }
}
