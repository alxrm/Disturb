package rm.com.disturb.ui;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.OnTextChanged;
import javax.inject.Inject;
import javax.inject.Provider;
import rm.com.disturb.R;
import rm.com.disturb.storage.Password;

/**
 * Created by alex
 */

public final class PasswordDialogFragment extends BaseDialogFragment {
  public static final String TAG_PASSWORD_DIALOG = "TAG_PASSWORD_DIALOG";

  @SuppressLint("StaticFieldLeak") //
  private static PasswordDialogFragment instance;

  @Inject @Password Provider<String> savedPassword;

  private String password = "";
  private OnPasswordConfirmationListener confirmationListener;

  public static void show(@NonNull FragmentManager fragmentManager,
      @NonNull OnPasswordConfirmationListener listener) {
    if (instance == null) {
      instance = newInstance();
    }

    fragmentManager.executePendingTransactions();

    if (!instance.isAdded()) {
      instance.setConfirmationListener(listener);
      instance.show(fragmentManager, TAG_PASSWORD_DIALOG);
    }
  }

  public static PasswordDialogFragment newInstance() {
    return new PasswordDialogFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    injector().inject(this);
  }

  @NonNull @Override
  protected View createView(@Nullable ViewGroup root, @NonNull LayoutInflater inflater) {
    return inflater.inflate(R.layout.fragment_dialog_password, root);
  }

  @Override protected void onCancelClick() {
    dismiss();
  }

  @Override protected void onSubmitClick() {
    if (savedPassword.get().equals(password)) {
      dismiss();
      confirmationListener.onPasswordConfirmed();
    } else {
      Toast.makeText(getActivity(), "The password didn't match", Toast.LENGTH_LONG).show();
    }
  }

  @Override public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    password = "";
  }

  public void setConfirmationListener(@NonNull OnPasswordConfirmationListener listener) {
    confirmationListener = listener;
  }

  @OnTextChanged(R.id.password_input) //
  void onPasswordChanged(@NonNull CharSequence nextPassword) {
    password = nextPassword.toString();
  }

  @NonNull @Override protected String title() {
    return "Confirm change";
  }

  @Override protected boolean cancelable() {
    return true;
  }

  public interface OnPasswordConfirmationListener {
    void onPasswordConfirmed();
  }
}
