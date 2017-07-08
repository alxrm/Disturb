package rm.com.disturb;

import dagger.Component;
import javax.inject.Singleton;
import rm.com.disturb.receiver.CallReceiver;
import rm.com.disturb.receiver.SmsReceiver;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess") //
@Singleton //
@Component(modules = DisturbModule.class) //
public interface DisturbComponent {
  void inject(MainActivity activity);

  void inject(CallReceiver receiver);

  void inject(SmsReceiver receiver);
}
