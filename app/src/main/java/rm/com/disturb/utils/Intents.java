package rm.com.disturb.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by alex
 */

public final class Intents {
  private Intents() {
  }

  public static boolean matches(@Nullable Intent intent, @NonNull String action) {
    return intent != null
        && action.equalsIgnoreCase(intent.getAction())
        && intent.getExtras() != null;
  }
}