package rm.com.disturb.telegram;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public interface Notifier {
  void notify(@NonNull String message);
}
