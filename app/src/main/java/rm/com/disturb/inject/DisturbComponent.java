package rm.com.disturb.inject;

import dagger.Component;
import javax.inject.Singleton;
import rm.com.disturb.data.receiver.CallReceiver;
import rm.com.disturb.data.receiver.SmsReceiver;
import rm.com.disturb.ui.LoginFragment;
import rm.com.disturb.ui.MainActivity;
import rm.com.disturb.ui.NotifyFragment;
import rm.com.disturb.ui.PasswordDialogFragment;

/**
 * Created by alex
 */

@Singleton //
@Component(modules = {
    DisturbModule.class,
    RulesModule.class
}) //
public interface DisturbComponent {
  void inject(MainActivity activity);

  void inject(NotifyFragment fragment);

  void inject(PasswordDialogFragment fragment);

  void inject(LoginFragment fragment);

  void inject(CallReceiver receiver);

  void inject(SmsReceiver receiver);
}
