package rm.com.disturb.data.resource;

import android.content.Context;
import android.support.annotation.NonNull;
import io.reactivex.Single;
import java8.util.Optional;

/**
 * Created by alex
 */
public interface Resource<T, O> {
  @NonNull Single<Optional<T>> load(@NonNull Context context, @NonNull O options);
}
