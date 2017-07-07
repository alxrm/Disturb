package rm.com.disturb;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

interface ContactBook {
  @NonNull String findName(@NonNull String phoneNumber);

  void findNameAsync(@NonNull String phoneNumber, @NonNull AsyncResult<String> resultHook);
}
