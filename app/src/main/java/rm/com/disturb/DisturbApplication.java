package rm.com.disturb;

import android.app.Application;
import io.paperdb.Paper;
import io.reactivex.plugins.RxJavaPlugins;
import rm.com.disturb.inject.DaggerDisturbComponent;
import rm.com.disturb.inject.DisturbComponent;
import rm.com.disturb.inject.DisturbModule;

/**
 * Created by alex
 */

public final class DisturbApplication extends Application {

  private DisturbComponent component;

  @Override public void onCreate() {
    super.onCreate();

    Paper.init(getApplicationContext());
    RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);

    component = DaggerDisturbComponent.builder()
        .application(this)
        .disturbModule(new DisturbModule())
        .build();
  }

  public DisturbComponent injector() {
    return component;
  }
}
