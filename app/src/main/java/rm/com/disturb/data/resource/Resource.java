package rm.com.disturb.data.resource;

import android.content.Context;
import android.support.annotation.NonNull;
import rm.com.disturb.data.async.PendingResult;

/**
 * Created by alex
 */
public interface Resource<T, O> {
  PendingResult<T> load(@NonNull Context context, @NonNull O options);
}
