package rm.com.disturb.data.async;

import android.support.annotation.NonNull;
import java8.util.Optional;

/**
 * Created by alex
 */

public interface Reply<T> {
  void ready(@NonNull Optional<T> result);
}