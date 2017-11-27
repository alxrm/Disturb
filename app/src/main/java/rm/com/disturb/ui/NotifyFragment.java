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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import rm.com.disturb.R;
import rm.com.disturb.data.storage.StringPreference;
import rm.com.disturb.data.telegram.command.TelegramCommand;
import rm.com.disturb.data.telegram.command.TelegramParams;
import rm.com.disturb.data.telegram.model.User;
import rm.com.disturb.data.telegram.source.Source;
import rm.com.disturb.inject.qualifier.ChatId;
import rm.com.disturb.inject.qualifier.Notify;
import rm.com.disturb.inject.qualifier.Password;
import rm.com.disturb.utils.Permissions;

import static rm.com.disturb.utils.UserFormats.avatarColorFilterOf;
import static rm.com.disturb.utils.UserFormats.iconLettersOf;

/**
 * Created by alex
 */

public final class NotifyFragment extends BaseFragment
    implements PasswordDialogFragment.OnPasswordConfirmationListener {
  private static final int REQ_PERMISSION = 1;

  @BindString(R.string.message_test_notification) String messageTestNotification;
  @BindString(R.string.description_test_notification) String descriptionTestNotification;
  @BindString(R.string.permissions_rationale) String permissionsRationale;
  @BindString(R.string.loading_text) String loadingText;
  @BindColor(R.color.color_icon_activated) int colorIconActivated;

  @BindView(R.id.notify_test_send) ImageView testCall;
  @BindView(R.id.notify_description_text) TextView description;

  @Inject @Notify TelegramCommand<Optional<String>> notify;
  @Inject @ChatId StringPreference chatIdPreference;
  @Inject @Password StringPreference passwordPreference;
  @Inject @ChatId Provider<String> chatId;
  @Inject Source<User, String> userSource;

  @NonNull public static NotifyFragment newInstance() {
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
    attachToolbar();
    loadUser();

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

    notify.send(TelegramParams.ofMessage(messageTestNotification)).subscribe();
  }
  // TODO We will need this later
  //@OnClick(R.id.notify_chat_id_change) void onChangeChatId() {
  //  if (passwordPreference.get().isEmpty()) {
  //    onPasswordConfirmed();
  //    return;
  //  }
  //
  //  PasswordDialogFragment.show(getFragmentManager(), this);
  //}

  private void loadUser() {
    userSource.retrieve(chatId.get())
        .filter(Optional::isPresent)
        .map(Optional::get)
        .subscribe(user -> {
          showUserInfo(user);
          loadAvatar(user.photoUrl());
        });
  }

  private void loadAvatar(@NonNull String photoUrl) {
    if (photoUrl.isEmpty() || avatar == null) {
      return;
    }

    Glide.with(this)
        .load(photoUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(avatar);
  }

  @SuppressWarnings("ConstantConditions") //
  private void showUserInfo(@NonNull User user) {
    avatarEmpty.setText(iconLettersOf(user.firstName()));
    avatarEmpty.getBackground().setColorFilter(avatarColorFilterOf(user.firstName()));

    title.setText(user.firstName() + " " + user.lastName());
    subtitle.setText(String.format("@%s", user.username()));

    if (user.username().isEmpty()) {
      subtitle.setVisibility(View.GONE);
      title.setTextSize(20);
    }
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
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return;
    }

    requestPermissions(new String[] {
        Manifest.permission.READ_CONTACTS, Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_PHONE_STATE
    }, REQ_PERMISSION);
  }
}
