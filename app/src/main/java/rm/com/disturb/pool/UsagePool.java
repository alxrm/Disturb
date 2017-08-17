package rm.com.disturb.pool;

import android.support.annotation.NonNull;
import java.util.List;

/**
 * Created by alex
 */

public interface UsagePool<T> {
  @NonNull List<Usage<T>> usages();

  void use(@NonNull T nextItem);
}

