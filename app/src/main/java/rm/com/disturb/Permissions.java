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

  static boolean isReceiveSmsPermissionGranted(@NonNull Context context) {
    return isPermissionGranted(context, Manifest.permission.RECEIVE_SMS);
  }

  static boolean isReadPhoneStatePermissionGranted(@NonNull Context context) {
    return isPermissionGranted(context, Manifest.permission.READ_PHONE_STATE);
  }

  static boolean isReadContactsPermissionGranted(@NonNull Context context) {
    return isPermissionGranted(context, Manifest.permission.READ_CONTACTS);
  }

  private static boolean isPermissionGranted(@NonNull Context context, @NonNull String permission) {
    return ContextCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED;
  }
}
