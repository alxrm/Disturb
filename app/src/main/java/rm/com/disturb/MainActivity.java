package rm.com.disturb;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

public final class MainActivity extends AppCompatActivity {
  private static final int REQ_PHONE_LISTENER_PERMISSIONS = 1;

  @BindString(R.string.message_test_notification) String messageTestNotification;
  @BindString(R.string.description_test_notification) String descriptionTestNotification;
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

    if (isPhoneListenerPermissionGranted()) {
      indicateNotificationsAvailable();
    } else {
      requestPhoneListenerPermissions();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode != REQ_PHONE_LISTENER_PERMISSIONS) {
      return;
    }

    if (isPhoneListenerPermissionGranted()) {
      indicateNotificationsAvailable();
    } else {
      Toast.makeText(this, permissionsRationale, Toast.LENGTH_LONG).show();
    }
  }

  private void indicateNotificationsAvailable() {
    description.setText(descriptionTestNotification);
    testCall.setColorFilter(colorAccent);
  }

  @OnClick(R.id.button_test_call) void onSendTestNotification() {
    if (!isPhoneListenerPermissionGranted()) {
      requestPhoneListenerPermissions();
      return;
    }

    notifier.notify(messageTestNotification);
  }

  private void requestPhoneListenerPermissions() {
    ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_PHONE_STATE },
        REQ_PHONE_LISTENER_PERMISSIONS);
  }

  private boolean isPhoneListenerPermissionGranted() {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        == PackageManager.PERMISSION_GRANTED;
  }
}
