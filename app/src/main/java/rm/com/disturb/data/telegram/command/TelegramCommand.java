package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import io.reactivex.Flowable;

/**
 * Created by alex
 */

public interface TelegramCommand<T> {
  @NonNull Flowable<T> send(@NonNull TelegramParams params);
}
