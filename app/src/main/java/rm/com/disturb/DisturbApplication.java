package rm.com.disturb;

import android.app.Application;
import android.content.IntentFilter;
import android.telephony.TelephonyManager;
import io.paperdb.Paper;
import io.reactivex.plugins.RxJavaPlugins;
import rm.com.disturb.data.receiver.CallReceiver;
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

    registerReceiver(new CallReceiver(),
        new IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED));
  }

  public DisturbComponent injector() {
    return component;
  }
}
