package rm.com.disturb;

import dagger.Component;
import javax.inject.Singleton;
import rm.com.disturb.receiver.CallReceiver;
import rm.com.disturb.receiver.SmsReceiver;
import rm.com.disturb.ui.LoginFragment;
import rm.com.disturb.ui.MainActivity;
import rm.com.disturb.ui.NotifyFragment;

/**
 * Created by alex
 */

@SuppressWarnings("WeakerAccess") //
@Singleton //
@Component(modules = DisturbModule.class) //
public interface DisturbComponent {
  void inject(MainActivity activity);

  void inject(NotifyFragment fragment);

  void inject(LoginFragment fragment);

  void inject(CallReceiver receiver);

  void inject(SmsReceiver receiver);
}
