package rm.com.disturb.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.inject.DisturbComponent;

/**
 * Created by alex
 */

public abstract class BaseDialogFragment extends DialogFragment
    implements DialogInterface.OnShowListener {

  final View.OnClickListener CANCEL = v -> onCancelClick();
  final View.OnClickListener SUBMIT = v -> onSubmitClick();

  private View rootView;
  private Unbinder unbinder;

  @Override public final void show(FragmentManager manager, String tag) {
    final FragmentTransaction ft = manager.beginTransaction();
    ft.add(this, tag);
    ft.commitAllowingStateLoss();
  }

  @Override public final void dismiss() {
    super.dismissAllowingStateLoss();
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
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

  @Override public void onShow(DialogInterface dialog) {
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
    final LayoutInflater inflater = getActivity().getLayoutInflater();

    rootView = createView(root, inflater);
    unbinder = ButterKnife.bind(this, rootView);

    return rootView;
  }

  @NonNull protected final DisturbComponent injector() {
    return ((DisturbApplication) getActivity().getApplication()).injector();
  }

  protected final void rebindButtons() {
    final Button positiveButton = ((Button) getDialog().findViewById(android.R.id.button1));
    final Button negativeButton = ((Button) getDialog().findViewById(android.R.id.button2));

    positiveButton.setOnClickListener(SUBMIT);
    negativeButton.setOnClickListener(CANCEL);
  }
}
