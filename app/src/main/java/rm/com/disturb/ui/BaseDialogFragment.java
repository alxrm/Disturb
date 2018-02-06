package rm.com.disturb.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.inject.DisturbComponent;
import rm.com.disturb.utils.Preconditions;

/**
 * Created by alex
 */

public abstract class BaseDialogFragment extends DialogFragment
    implements DialogInterface.OnShowListener {

  final View.OnClickListener CANCEL = v -> onCancelClick();
  final View.OnClickListener SUBMIT = v -> onSubmitClick();

  protected View rootView;
  private Unbinder unbinder;

  @Override public final void show(@NonNull FragmentManager manager, @NonNull String tag) {
    final FragmentTransaction ft = manager.beginTransaction();
    final Fragment prev = manager.findFragmentByTag(tag);

    if (prev != null) {
      ft.remove(prev);
    }

    ft.add(this, tag);
    ft.commitAllowingStateLoss();
  }

  @Override public final void dismiss() {
    super.dismissAllowingStateLoss();
  }

  @NonNull @Override public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()) //
        .setView(view())
        .setTitle(title())
        .setPositiveButton(android.R.string.ok, null)
        .setNegativeButton(android.R.string.cancel, null);

    final Dialog dialog = builder.create();
    dialog.setCancelable(cancelable());
    dialog.setCanceledOnTouchOutside(cancelable());
    dialog.setOnShowListener(this);

    return dialog;
  }

  @Override public void onShow(@NonNull DialogInterface dialog) {
    rebindButtons();
  }

  @Override public void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @NonNull
  protected abstract View createView(@Nullable ViewGroup root, @NonNull LayoutInflater inflater);

  @NonNull protected abstract String title();

  protected abstract boolean cancelable();

  protected abstract void onCancelClick();

  protected abstract void onSubmitClick();

  @NonNull protected final View view() {
    final ViewGroup root = (ViewGroup) getView();
    final LayoutInflater inflater = parent().getLayoutInflater();

    rootView = createView(root, inflater);
    unbinder = ButterKnife.bind(this, rootView);

    return rootView;
  }

  @NonNull final protected AppCompatActivity parent() {
    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    Preconditions.checkNotNull(activity, "Parent activity was null, you are doing something wrong");

    return activity;
  }

  @NonNull protected final DisturbComponent injector() {
    return ((DisturbApplication) parent().getApplication()).injector();
  }

  protected final void rebindButtons() {
    final Button positiveButton = getDialog().findViewById(android.R.id.button1);
    final Button negativeButton = getDialog().findViewById(android.R.id.button2);

    positiveButton.setOnClickListener(SUBMIT);
    negativeButton.setOnClickListener(CANCEL);
  }
}
