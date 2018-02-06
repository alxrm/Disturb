package rm.com.disturb.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.mikhaellopez.circularimageview.CircularImageView;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.R;
import rm.com.disturb.inject.DisturbComponent;
import rm.com.disturb.utils.Preconditions;

/**
 * Created by alex
 */

public class BaseFragment extends Fragment {
  @BindView(R.id.toolbar) @Nullable Toolbar toolbar;
  @BindView(R.id.toolbar_title) @Nullable TextView title;
  @BindView(R.id.toolbar_subtitle) @Nullable TextView subtitle;
  @BindView(R.id.toolbar_avatar) @Nullable CircularImageView avatar;
  @BindView(R.id.toolbar_avatar_empty) @Nullable TextView avatarEmpty;

  protected Unbinder unbinder;

  @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
    injector().inject(this);
  }

  @Override public void onDestroy() {
    super.onDestroy();
    unbinder.unbind();
  }

  @NonNull final protected Navigation navigator() {
    return (Navigation) parent();
  }

  protected void navigateBack() {
    navigator().back();
  }

  final protected void navigateTo(@NonNull Fragment fragment) {
    navigator().to(fragment);
  }

  @NonNull final protected DisturbComponent injector() {
    return ((DisturbApplication) parent().getApplication()).injector();
  }

  @NonNull final protected AppCompatActivity parent() {
    final AppCompatActivity activity = (AppCompatActivity) getActivity();
    Preconditions.checkNotNull(activity, "Parent activity was null, you are doing something wrong");

    return activity;
  }

  @NonNull final protected FragmentManager fragmentManager() {
    final FragmentManager manager = getFragmentManager();
    Preconditions.checkNotNull(manager, "Fragment manager was null, you are doing something wrong");
    return manager;
  }

  final protected void attachToolbar() {
    Preconditions.checkNotNull(toolbar, "Toolbar was null, please add <Toolbar/> in your layout");
    Preconditions.checkNotNull(title, "Title was null, please take a look at the code");

    parent().setSupportActionBar(toolbar);

    title.setTypeface(ResourcesCompat.getFont(parent(), R.font.roboto_medium_family));
  }
}
