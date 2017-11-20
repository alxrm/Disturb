package rm.com.disturb.data.async;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

/**
 * Created by alex
 */

public interface Transform<T, R> {
  @WorkerThread @NonNull R apply(@NonNull T input) throws Exception;
}
