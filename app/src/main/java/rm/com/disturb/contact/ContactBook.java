package rm.com.disturb.contact;

import android.support.annotation.NonNull;
import rm.com.disturb.utils.AsyncResult;

/**
 * Created by alex
 */

public interface ContactBook {
  @NonNull String findName(@NonNull String phoneNumber);

  void findNameAsync(@NonNull String phoneNumber, @NonNull AsyncResult<String> resultHook);
}
