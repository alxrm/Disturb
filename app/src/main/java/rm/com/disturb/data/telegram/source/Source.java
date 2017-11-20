package rm.com.disturb.data.telegram.source;

import android.support.annotation.NonNull;
import rm.com.disturb.data.async.PendingResult;

/**
 * Created by alex
 */

public interface Source<T, P> {
  @NonNull PendingResult<T> retrieve(@NonNull P parameters);
}
