package rm.com.disturb.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import javax.inject.Inject;
import rm.com.disturb.R;
import rm.com.disturb.async.AsyncResult;
import rm.com.disturb.storage.ChatId;
import rm.com.disturb.storage.Password;
import rm.com.disturb.storage.StringPreference;
import rm.com.disturb.telegram.Auth;

/**
 * Created by alex
 */

public final class LoginFragment extends BaseFragment {

  @Inject @ChatId StringPreference chatIdPreference;
  @Inject @Password StringPreference passwordPreference;
  @Inject Auth auth;

  private ProgressDialog progressDialog;

  @NonNull private String chatId = "";
  @NonNull private String password = "";

  public static LoginFragment newInstance() {
    return new LoginFragment();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_login, container, false);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    injector().inject(this);
    toggleActionBar(false);
  }

  @OnClick(R.id.login_save) void onSave() {
    if (progressDialog != null && progressDialog.isShowing()) {
      return;
    }

    showProgressDialog();

    auth.authorizeAsync(chatId, new AsyncResult<Boolean>() {
      @Override public void ready(@NonNull Boolean result) {
        if (result) {
          proceedToNotifyFragment();
        } else {
          showError();
        }
      }
    });
  }

  @OnClick(R.id.login_activate_bot) void onActivateBot() {
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://telegram.me/disturb_me_bot")));
  }

  @OnClick(R.id.login_chat_id_get) void onGetChatId() {
    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://telegram.me/IDgrambot")));
  }

  @OnTextChanged(R.id.login_chat_id_input) //
  void onChatIdChanged(@NonNull CharSequence nextChatId) {
    chatId = nextChatId.toString();
  }

  @OnTextChanged(R.id.login_password_input) //
  void onPasswordChanged(@NonNull CharSequence nextPassword) {
    password = nextPassword.toString();
  }

  private void proceedToNotifyFragment() {
    if (progressDialog == null) {
      return;
    }

    cancelDialog();
    passwordPreference.set(password);
    chatIdPreference.set(chatId);
    navigateTo(NotifyFragment.newInstance());
  }

  private void showError() {
    final View rootView = getView();
    cancelDialog();

    if (rootView == null) {
      return;
    }

    Snackbar.make(rootView, "The Bot couldn't connect to the provided Chat ID",
        Snackbar.LENGTH_LONG).show();
  }

  private void cancelDialog() {
    progressDialog.cancel();
    progressDialog = null;
  }

  private void showProgressDialog() {
    progressDialog =
        ProgressDialog.show(getActivity(), "Auth", "Checking the bot connection...", true);
  }
}