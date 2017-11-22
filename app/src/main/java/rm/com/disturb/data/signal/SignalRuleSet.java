package rm.com.disturb.data.signal;

import android.support.annotation.NonNull;
import java.util.Set;
import java8.util.stream.StreamSupport;

public final class SignalRuleSet implements RuleSet<MessageSignal> {

  private final Set<Rule<MessageSignal>> rules;

  public SignalRuleSet(@NonNull Set<Rule<MessageSignal>> rules) {
    this.rules = rules;
  }

  @NonNull @Override public Set<Rule<MessageSignal>> rules() {
    return rules;
  }

  @Override public boolean shouldApply(@NonNull MessageSignal item) {
    return !item.type().equals(MessageSignals.EMPTY);
  }

  @Override public void apply(@NonNull MessageSignal nextItem) {
    if (!shouldApply(nextItem)) {
      return;
    }

    StreamSupport.stream(rules)
        .filter(currentRule -> currentRule.shouldApply(nextItem))
        .forEach(currentRule -> currentRule.apply(nextItem));
  }
}
