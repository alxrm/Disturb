package rm.com.disturb.data.rule;

import android.support.annotation.NonNull;
import java.util.List;

/**
 * Created by alex
 */

public interface RulePool<T> {
  @NonNull List<Rule<T>> rules();

  void consume(@NonNull T nextItem);
}

