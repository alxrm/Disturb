package rm.com.disturb;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * Created by alex
 */

final class Permissions {

  private Permissions() {
  }

  static boolean isReadPhoneStatePermissionGranted(@NonNull Context context) {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
        == PackageManager.PERMISSION_GRANTED;
  }

  static boolean isReadContactsPermissionGranted(@NonNull Context context) {
    return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
        == PackageManager.PERMISSION_GRANTED;
  }
}
