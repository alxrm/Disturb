package rm.com.disturb;

import dagger.Component;
import javax.inject.Singleton;

/**
 * Created by alex
 */

@Singleton //
@Component(modules = DisturbModule.class) //
interface DisturbComponent {
  void inject(MainActivity activity);

  void inject(CallReceiver receiver);

  void inject(SmsReceiver receiver);
}
