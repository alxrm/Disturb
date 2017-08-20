package rm.com.disturb;

import android.app.Application;
import rm.com.disturb.inject.DisturbModule;

/**
 * Created by alex
 */

public final class DisturbApplication extends Application {

  private DisturbComponent component;

  @Override public void onCreate() {
    super.onCreate();
    component = DaggerDisturbComponent.builder().disturbModule(new DisturbModule(this)).build();
  }

  final public DisturbComponent injector() {
    return component;
  }
}
