package rm.com.disturb.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.inject.DisturbComponent;

/**
 * Created by alex
 */

public class BaseFragment extends Fragment {
  protected Unbinder unbinder;

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  protected void navigateBack() {
    final Navigation navigation = navigator();

    if (navigation != null) {
      navigation.back();
    }
  }

  @Nullable final protected Navigation navigator() {
    return (Navigation) getActivity();
  }

  final protected void navigateTo(@NonNull Fragment fragment) {
    final Navigation navigation = navigator();

    if (navigation != null) {
      navigation.to(fragment);
    }
  }

  @NonNull final protected DisturbComponent injector() {
    return ((DisturbApplication) getActivity().getApplication()).injector();
  }

  final protected void toggleActionBar(boolean show) {
    final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

    if (actionBar == null) {
      return;
    }

    if (show) {
      actionBar.show();
    } else {
      actionBar.hide();
    }
  }
}
