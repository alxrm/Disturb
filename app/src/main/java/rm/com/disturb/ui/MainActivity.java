package rm.com.disturb.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.Unbinder;
import javax.inject.Inject;
import javax.inject.Provider;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.R;
import rm.com.disturb.storage.ChatId;

public final class MainActivity extends AppCompatActivity implements Navigator {
  static String KEY_FRAGMENT_SAVE = "KEY_FRAGMENT_SAVE";

  @Inject @ChatId Provider<String> chatId;

  protected Fragment currentFragment;
  protected Unbinder unbinder;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ((DisturbApplication) getApplication()).injector().inject(this);

  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    getFragmentManager().putFragment(outState, KEY_FRAGMENT_SAVE, currentFragment);
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    if (unbinder != null) {
      unbinder.unbind();
    }
  }

  @Override public void to(@NonNull Fragment dest) {
    changeFragment(dest, true);
  }

  @Override public void back() {
    onBackPressed();
  }

  final protected void changeFragment(@NonNull Fragment fragment, boolean isRoot) {
    this.currentFragment = fragment;

    final FragmentTransaction fragmentTransaction =
        getFragmentManager().beginTransaction().replace(R.id.container, fragment);

    if (!isRoot) {
      fragmentTransaction.addToBackStack(null);
    }

    fragmentTransaction.commit();
  }

  @NonNull final protected Fragment getInitialFragment(@Nullable Bundle state,
      @NonNull Fragment defaultFragment) {
    return (state == null) ? defaultFragment
        : getFragmentManager().getFragment(state, KEY_FRAGMENT_SAVE);
  }
}