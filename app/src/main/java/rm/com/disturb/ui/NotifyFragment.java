package rm.com.disturb.ui;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import javax.inject.Provider;
import rm.com.disturb.R;
import rm.com.disturb.storage.ChatId;
import rm.com.disturb.storage.Password;
import rm.com.disturb.storage.StringPreference;
import rm.com.disturb.telegram.Notifier;
import rm.com.disturb.utils.Permissions;

/**
 * Created by alex
 */

public final class NotifyFragment extends BaseFragment
    implements PasswordDialogFragment.OnPasswordConfirmationListener {
  private static final int REQ_PERMISSION = 1;

  @BindString(R.string.message_test_notification) String messageTestNotification;
  @BindString(R.string.description_test_notification) String descriptionTestNotification;
  @BindString(R.string.permissions_rationale) String permissionsRationale;
  @BindColor(R.color.color_icon_activated) int colorIconActivated;

  @BindView(R.id.notify_test_send) ImageView testCall;
  @BindView(R.id.notify_description_text) TextView description;
  @BindView(R.id.notify_chat_id_text) TextView chatIdText;

  @Inject Notifier notifier;
  @Inject @ChatId StringPreference chatIdPreference;
  @Inject @Password StringPreference passwordPreference;
  @Inject @ChatId Provider<String> chatId;
  @Inject @Password Provider<String> password;

  public static NotifyFragment newInstance() {
    return new NotifyFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_notify, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    injector().inject(this);
    toggleActionBar(true);

    chatIdText.setText(getString(R.string.text_chat_id, chatId.get()));

    if (areAnyPermissionsGranted()) {
      indicateNotificationsAvailable();
    }

    if (!areAllPermissionsGranted()) {
      requestAllPermissions();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode != REQ_PERMISSION) {
      return;
    }

    if (areAnyPermissionsGranted()) {
      indicateNotificationsAvailable();
    } else {
      Toast.makeText(getActivity(), permissionsRationale, Toast.LENGTH_LONG).show();
    }
  }

  @Override public void onPasswordConfirmed() {
    passwordPreference.delete();
    chatIdPreference.delete();
    navigateTo(LoginFragment.newInstance());
  }

  @OnClick(R.id.notify_test_send) void onSendTestNotification() {
    if (!areAnyPermissionsGranted()) {
      requestAllPermissions();
      return;
    }

    notifier.notify(messageTestNotification);
  }

  @OnClick(R.id.notify_chat_id_change) void onChangeChatId() {
    PasswordDialogFragment.show(getFragmentManager(), this);
  }

  private boolean areAnyPermissionsGranted() {
    return Permissions.isReadPhoneStatePermissionGranted(getActivity())
        || Permissions.isReceiveSmsPermissionGranted(getActivity());
  }

  private boolean areAllPermissionsGranted() {
    return Permissions.isReadPhoneStatePermissionGranted(getActivity())
        && Permissions.isReceiveSmsPermissionGranted(getActivity());
  }

  private void indicateNotificationsAvailable() {
    description.setText(descriptionTestNotification);
    testCall.setAlpha(1.0F);
    testCall.setColorFilter(colorIconActivated);
  }

  private void requestAllPermissions() {
    if (Build.VERSION.SDK_INT < 23) {
      return;
    }

    requestPermissions(new String[] {
        Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_PHONE_STATE
    }, REQ_PERMISSION);
  }
}
