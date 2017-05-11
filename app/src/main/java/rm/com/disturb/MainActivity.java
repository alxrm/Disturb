package rm.com.disturb;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.canelmas.let.AskPermission;
import com.canelmas.let.DeniedPermission;
import com.canelmas.let.Let;
import com.canelmas.let.RuntimePermissionListener;
import com.canelmas.let.RuntimePermissionRequest;
import java.util.List;
import javax.inject.Inject;

public final class MainActivity extends AppCompatActivity implements RuntimePermissionListener {

  @BindString(R.string.message_test_notification) String messageTestNotification;
  @BindString(R.string.description_test_notification) String descriptionTestNotification;
  @BindColor(R.color.color_accent) int colorAccent;

  @BindView(R.id.button_test_call) ImageView testCall;
  @BindView(R.id.text_description) TextView description;

  @Inject Notifier notifier;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ButterKnife.bind(this);
    ((DisturbApplication) getApplicationContext()).injector().inject(this);

    authorizeIfNeeded();
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    Let.handle(this, requestCode, permissions, grantResults);
  }

  @Override public void onShowPermissionRationale(List<String> permissionList,
      RuntimePermissionRequest permissionRequest) {
    Toast.makeText(this, "This app would not work without these permissions", Toast.LENGTH_LONG)
        .show();
  }

  @Override public void onPermissionDenied(List<DeniedPermission> deniedPermissionList) {
  }

  @AskPermission({
      Manifest.permission.READ_PHONE_STATE
  }) private void authorizeIfNeeded() {
    description.setText(descriptionTestNotification);
    testCall.setColorFilter(colorAccent);
  }

  @OnClick(R.id.button_test_call) void onSendTestNotification() {
    if (!isPhoneListenerPermissionGranted()) {
      authorizeIfNeeded();
      return;
    }

    notifier.notify(messageTestNotification);
  }

  private boolean isPhoneListenerPermissionGranted() {
    return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        == PackageManager.PERMISSION_GRANTED;
  }
}
