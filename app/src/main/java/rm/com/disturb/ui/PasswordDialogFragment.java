package rm.com.disturb.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import javax.inject.Inject;
import javax.inject.Provider;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.DisturbComponent;
import rm.com.disturb.R;
import rm.com.disturb.storage.Password;

/**
 * Created by alex
 */

public final class PasswordDialogFragment extends DialogFragment {
  public static final String TAG_PASSWORD_DIALOG = "TAG_PASSWORD_DIALOG";

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

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setView(createView());
    builder.setTitle("Confirm change");
    builder.setPositiveButton(android.R.string.ok, onSubmit());
    builder.setNegativeButton(android.R.string.cancel, null);

    final Dialog dialog = builder.create();
    dialog.setCancelable(true);
    dialog.setCanceledOnTouchOutside(true);

    return builder.show();
  }

  private DialogInterface.OnClickListener onSubmit() {
    return new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        Log.e("DBG", "onSubmit" + savedPassword.get() + " " + password);
        if (savedPassword.get().equals(password)) {
          confirmationListener.onPasswordConfirmed();
        } else {
          Toast.makeText(getActivity(), "The password didn't match", Toast.LENGTH_LONG).show();
        }
      }
    };
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

  private View createView() {
    final ViewGroup root = (ViewGroup) getView();
    final View dialogLayout =
        getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_password, root);

    ButterKnife.bind(this, dialogLayout);

    return dialogLayout;
  }

  @NonNull final protected DisturbComponent injector() {
    return ((DisturbApplication) getActivity().getApplication()).injector();
  }

  public interface OnPasswordConfirmationListener {
    void onPasswordConfirmed();
  }
}
