package rm.com.disturb.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.DisturbComponent;

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
    final Navigator navigator = navigator();

    if (navigator != null) {
      navigator.back();
    }
  }

  @Nullable final protected Navigator navigator() {
    return (Navigator) getActivity();
  }

  final protected void navigateTo(@NonNull Fragment fragment) {
    final Navigator navigator = navigator();

    if (navigator != null) {
      navigator.to(fragment);
    }
  }

  @NonNull final protected DisturbComponent injector() {
    return ((DisturbApplication) getActivity().getApplication()).injector();
  }
}
