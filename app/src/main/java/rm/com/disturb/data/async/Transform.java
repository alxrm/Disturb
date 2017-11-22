package rm.com.disturb.data.async;

import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import java8.util.Optional;

/**
 * Created by alex
 */

public interface Transform<T, R> {
  @WorkerThread @NonNull Optional<R> apply(@NonNull Optional<T> input) throws Exception;
}
