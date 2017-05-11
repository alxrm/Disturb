package rm.com.disturb;

import android.app.Application;

/**
 * Created by alex
 */

public final class DisturbApplication extends Application {

  private DisturbComponent component;

  @Override public void onCreate() {
    super.onCreate();
    component = DaggerDisturbComponent.builder().build();
  }

  final DisturbComponent injector() {
    return component;
  }
}
