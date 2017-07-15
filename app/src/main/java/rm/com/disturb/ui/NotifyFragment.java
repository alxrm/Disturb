package rm.com.disturb.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import javax.inject.Inject;
import rm.com.disturb.R;
import rm.com.disturb.storage.ChatId;
import rm.com.disturb.storage.StringPreference;
import rm.com.disturb.telegram.Notifier;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

public final class NotifyFragment extends BaseFragment {
  private static final int REQ_PERMISSION = 1;

  @BindString(R.string.message_test_notification) String messageTestNotification;
  @BindString(R.string.description_test_notification) String descriptionTestNotification;
  @BindString(R.string.permissions_rationale) String permissionsRationale;
  @BindColor(R.color.color_icon_activated) int colorIconActivated;

  @BindView(R.id.button_test_call) ImageView testCall;
  @BindView(R.id.text_description) TextView description;

  @Inject @ChatId StringPreference chatIdPreference;
  @Inject Notifier notifier;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_notify, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    injector().inject(this);

    if (isAnyOfPermissionsGranted()) {
      indicateNotificationsAvailable();
    }

    if (!isAllOfPermissionsGranted()) {
      requestAllPermissions();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode != REQ_PERMISSION) {
      return;
    }

    if (isAnyOfPermissionsGranted()) {
      indicateNotificationsAvailable();
    } else {
      Toast.makeText(getActivity(), permissionsRationale, Toast.LENGTH_LONG).show();
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
    return Permissions.isReadPhoneStatePermissionGranted(getActivity())
        || Permissions.isReceiveSmsPermissionGranted(getActivity());
  }

  private boolean isAllOfPermissionsGranted() {
    return Permissions.isReadPhoneStatePermissionGranted(getActivity())
        && Permissions.isReceiveSmsPermissionGranted(getActivity());
  }

  private void indicateNotificationsAvailable() {
    description.setText(descriptionTestNotification);
    testCall.setColorFilter(colorIconActivated);
  }

  private void requestAllPermissions() {
    ActivityCompat.requestPermissions(getActivity(), new String[] {
        Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_PHONE_STATE
    }, REQ_PERMISSION);
  }
}
