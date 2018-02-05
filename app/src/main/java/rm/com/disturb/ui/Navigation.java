package rm.com.disturb.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by alex
 */

interface Navigation {
  void to(@NonNull Fragment dest);

  void back();
}