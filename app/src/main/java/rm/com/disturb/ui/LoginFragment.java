package rm.com.disturb.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import rm.com.disturb.R;

/**
 * Created by alex
 */

public final class LoginFragment extends BaseFragment {

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

    final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

    if (actionBar != null) {
      actionBar.hide();
    }
  }
}
