package rm.com.disturb.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * Created by alex
 */

public final class Permissions {

  private Permissions() {
  }

  public static boolean isReceiveSmsPermissionGranted(@NonNull Context context) {
    return isPermissionGranted(context, Manifest.permission.RECEIVE_SMS);
  }

  public static boolean isReadPhoneStatePermissionGranted(@NonNull Context context) {
    return isPermissionGranted(context, Manifest.permission.READ_PHONE_STATE);
  }

  public static boolean isReadContactsPermissionGranted(@NonNull Context context) {
    return isPermissionGranted(context, Manifest.permission.READ_CONTACTS);
  }

  private static boolean isPermissionGranted(@NonNull Context context, @NonNull String permission) {
    return ContextCompat.checkSelfPermission(context, permission)
        == PackageManager.PERMISSION_GRANTED;
  }
}
