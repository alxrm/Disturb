package rm.com.disturb.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.util.List;
import rm.com.disturb.R;
import rm.com.disturb.ui.adapter.SheetAdapter;
import rm.com.disturb.ui.holder.BaseHolder;
import rm.com.disturb.ui.model.SheetItem;

/**
 * Created by alex
 */

public final class ActionsBottomSheetFragment extends BottomSheetDialogFragment
    implements BaseHolder.OnClickListener<SheetItem> {
  public static final String TAG_ACTIONS_SHEET_DIALOG = "TAG_ACTIONS_SHEET_DIALOG";

  @BindView(R.id.bottom_sheet_actions) RecyclerView actionsView;

  private final SheetAdapter adapter = new SheetAdapter();

  private Unbinder unbinder;
  private BaseHolder.OnClickListener<SheetItem> actionListener = (position, item) -> {
  };

  @NonNull public static ActionsBottomSheetFragment show(@NonNull FragmentManager manager) {
    final ActionsBottomSheetFragment sheetFragment = newInstance();

    manager.executePendingTransactions();

    sheetFragment.show(manager, TAG_ACTIONS_SHEET_DIALOG);
    return sheetFragment;
  }

  @NonNull public static ActionsBottomSheetFragment newInstance() {
    return new ActionsBottomSheetFragment();
  }

  @Override public void show(@NonNull FragmentManager manager, @NonNull String tag) {
    final FragmentTransaction ft = manager.beginTransaction();
    final Fragment prev = manager.findFragmentByTag(tag);

    if (prev != null) {
      ft.remove(prev);
    }

    ft.add(this, tag);
    ft.commitAllowingStateLoss();
  }

  @Override public void dismiss() {
    super.dismissAllowingStateLoss();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.bottom_sheet_layout, container);
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);

    adapter.setOnClickListener(this);
    actionsView.setAdapter(adapter);
  }

  @Override public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  @Override public void onItemClick(int position, @NonNull SheetItem item) {
    dismiss();
    actionListener.onItemClick(position, item);
  }

  @NonNull public ActionsBottomSheetFragment setActions(@NonNull List<SheetItem> actions) {
    adapter.updateData(actions);
    return this;
  }

  @NonNull public ActionsBottomSheetFragment setActionListener(
      @NonNull BaseHolder.OnClickListener<SheetItem> listener) {
    this.actionListener = listener;
    return this;
  }
}
