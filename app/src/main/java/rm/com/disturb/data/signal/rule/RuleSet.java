package rm.com.disturb.data.signal.rule;

import android.support.annotation.NonNull;
import java.util.Set;

/**
 * Created by alex
 */

public interface RuleSet<T> extends Rule<T> {
  @NonNull Set<Rule<T>> rules();
}

