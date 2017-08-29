package rm.com.disturb.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import javax.inject.Inject;
import javax.inject.Provider;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.R;
import rm.com.disturb.inject.qualifier.ChatId;
import rm.com.disturb.inject.qualifier.Password;

public final class MainActivity extends AppCompatActivity implements Navigation {
  static String KEY_FRAGMENT_SAVE = "KEY_FRAGMENT_SAVE";

  @Inject @ChatId Provider<String> chatId;
  @Inject @Password Provider<String> password;

  protected Fragment currentFragment;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ((DisturbApplication) getApplication()).injector().inject(this);

    currentFragment = getInitialFragment(savedInstanceState,
        areFieldsEmpty() ? LoginFragment.newInstance() : NotifyFragment.newInstance());

    to(currentFragment);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    getFragmentManager().putFragment(outState, KEY_FRAGMENT_SAVE, currentFragment);
  }

  @Override public void to(@NonNull Fragment dest) {
    changeFragment(dest, true);
  }

  @Override public void back() {
    onBackPressed();
  }

  private void changeFragment(@NonNull Fragment fragment, boolean isRoot) {
    this.currentFragment = fragment;

    final FragmentTransaction fragmentTransaction =
        getFragmentManager().beginTransaction().replace(R.id.container, fragment);

    if (!isRoot) {
      fragmentTransaction.addToBackStack(null);
    }

    fragmentTransaction.commit();
  }

  @NonNull private Fragment getInitialFragment(@Nullable Bundle state,
      @NonNull Fragment defaultFragment) {
    return (state == null) ? defaultFragment
        : getFragmentManager().getFragment(state, KEY_FRAGMENT_SAVE);
  }

  private boolean areFieldsEmpty() {
    return chatId.get().isEmpty() || password.get().isEmpty();
  }
}