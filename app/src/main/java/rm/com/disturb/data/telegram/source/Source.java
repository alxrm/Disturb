package rm.com.disturb.data.telegram.source;

import android.support.annotation.NonNull;
import io.reactivex.Single;
import java8.util.Optional;

/**
 * Created by alex
 */

public interface Source<T, P> {
  @NonNull Single<Optional<T>> retrieve(@NonNull P parameters);
}
