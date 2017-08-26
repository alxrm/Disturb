package rm.com.disturb.data.contact;

import android.support.annotation.NonNull;
import rm.com.disturb.data.async.PendingResult;

/**
 * Created by alex
 */

public interface ContactBook {
  @NonNull PendingResult<String> findName(@NonNull String phoneNumber);
}
