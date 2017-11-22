package rm.com.disturb.data.resource;

import android.content.Context;
import android.support.annotation.NonNull;
import io.reactivex.Flowable;
import java8.util.Optional;

/**
 * Created by alex
 */
public interface Resource<T, O> {
  @NonNull Flowable<Optional<T>> load(@NonNull Context context, @NonNull O options);
}
