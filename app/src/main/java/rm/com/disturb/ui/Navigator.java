package rm.com.disturb.ui;

import android.app.Fragment;
import android.support.annotation.NonNull;

/**
 * Created by alex
 */

interface Navigator {
  void to(@NonNull Fragment dest);

  void back();
}