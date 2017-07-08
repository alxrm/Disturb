package rm.com.disturb;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import rm.com.disturb.telegram.Notifier;
import rm.com.disturb.utils.Permissions;

public final class MainActivity extends AppCompatActivity {
  private static final int REQ_PERMISSION = 1;

  @BindString(R.string.message_test_notification) String messageTestNotification;
  @BindString(R.string.description_test_notification) String descriptionTestNotification;
  @BindString(R.string.description_wait_permissions) String descriptionWaitPermission;
  @BindString(R.string.permissions_rationale) String permissionsRationale;
  @BindColor(R.color.color_accent) int colorAccent;

  @BindView(R.id.button_test_call) ImageView testCall;
  @BindView(R.id.text_description) TextView description;

  @Inject Notifier notifier;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);
    ((DisturbApplication) getApplicationContext()).injector().inject(this);

    if (isAnyOfPermissionsGranted()) {
      indicateNotificationsAvailable();
    }

    requestAllPermissions();
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode != REQ_PERMISSION) {
      return;
    }

    if (isAnyOfPermissionsGranted()) {
      indicateNotificationsAvailable();
    } else {
      Toast.makeText(this, permissionsRationale, Toast.LENGTH_LONG).show();
    }
  }

  @OnClick(R.id.button_test_call) void onSendTestNotification() {
    if (!isAnyOfPermissionsGranted()) {
      requestAllPermissions();
      return;
    }

    notifier.notify(messageTestNotification);
  }

  private boolean isAnyOfPermissionsGranted() {
    return Permissions.isReadPhoneStatePermissionGranted(this)
        || Permissions.isReceiveSmsPermissionGranted(this);
  }

  private void indicateNotificationsAvailable() {
    description.setText(descriptionTestNotification);
    testCall.setColorFilter(colorAccent);
  }

  private void requestAllPermissions() {
    ActivityCompat.requestPermissions(this, new String[] {
        Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_PHONE_STATE
    }, REQ_PERMISSION);
  }
}
