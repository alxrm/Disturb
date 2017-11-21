package rm.com.disturb.ui;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.mikhaellopez.circularimageview.CircularImageView;
import javax.inject.Inject;
import rm.com.disturb.DisturbApplication;
import rm.com.disturb.R;
import rm.com.disturb.data.resource.Resource;
import rm.com.disturb.inject.DisturbComponent;
import rm.com.disturb.utils.Preconditions;

/**
 * Created by alex
 */

public class BaseFragment extends Fragment {
  private static final String PATH_TOOLBAR_TYPEFACE = "Roboto-Medium.ttf";

  @BindView(R.id.toolbar) @Nullable Toolbar toolbar;
  @BindView(R.id.toolbar_avatar) @Nullable CircularImageView avatar;
  @BindView(R.id.toolbar_title) @Nullable TextView title;
  @BindView(R.id.toolbar_subtitle) @Nullable TextView subtitle;

  @Inject Resource<Typeface, String> typefaceResource;

  protected Unbinder unbinder;

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
    injector().inject(this);
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

  @NonNull final protected AppCompatActivity parent() {
    final AppCompatActivity activity = (AppCompatActivity) getActivity();

    Preconditions.checkNotNull(activity, "Parent activity was null, you are doing something wrong");

    return activity;
  }

  final protected void attachToolbar() {
    Preconditions.checkNotNull(toolbar, "Toolbar was null, please add <Toolbar/> in your layout");
    Preconditions.checkNotNull(title, "Title was null, please take a look at the code");

    parent().setSupportActionBar(toolbar);

    typefaceResource.load(parent(), PATH_TOOLBAR_TYPEFACE)
        .whenReady(result -> title.setTypeface(result));
  }
}
