package rm.com.disturb.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import javax.inject.Inject;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.R;
import rm.com.disturb.data.storage.StringPreference;
import rm.com.disturb.inject.qualifier.ChatId;
import rm.com.disturb.inject.qualifier.Password;

public final class MainActivity extends AppCompatActivity implements Navigation {
  static String KEY_FRAGMENT_SAVE = "KEY_FRAGMENT_SAVE";

  @Inject @ChatId StringPreference chatId;
  @Inject @Password StringPreference password;

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
    getSupportFragmentManager().putFragment(outState, KEY_FRAGMENT_SAVE, currentFragment);
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
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);

    if (!isRoot) {
      fragmentTransaction.addToBackStack(null);
    }

    fragmentTransaction.commit();
  }

  @NonNull
  private Fragment getInitialFragment(@Nullable Bundle state, @NonNull Fragment defaultFragment) {
    return (state == null) ? defaultFragment
        : getSupportFragmentManager().getFragment(state, KEY_FRAGMENT_SAVE);
  }

  private boolean areFieldsEmpty() {
    return !chatId.isSet() || !password.isSet();
  }
}