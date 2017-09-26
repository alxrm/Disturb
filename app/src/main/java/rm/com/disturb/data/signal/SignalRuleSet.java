package rm.com.disturb.data.signal;

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

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return !item.type().equals(Signals.EMPTY);
  }

  @Override public void apply(@NonNull MessageSignal nextItem) {
    if (!shouldApply(nextItem)) {
      return;
    }

    for (int i = 0, size = rules.size(); i < size; i++) {
      final Rule<MessageSignal> currentRule = rules.get(i);

      if (currentRule.shouldApply(nextItem)) {
        currentRule.apply(nextItem);
      }
    }
  }
}
