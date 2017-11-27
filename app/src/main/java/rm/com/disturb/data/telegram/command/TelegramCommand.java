package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import io.reactivex.Single;

/**
 * Created by alex
 */

public interface TelegramCommand<T> {
  @NonNull Single<T> send(@NonNull TelegramParams params);
}
