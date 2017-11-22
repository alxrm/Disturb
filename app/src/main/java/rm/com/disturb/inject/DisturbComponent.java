package rm.com.disturb.inject;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import javax.inject.Singleton;
import rm.com.disturb.data.receiver.CallReceiver;
import rm.com.disturb.data.receiver.SmsReceiver;
import rm.com.disturb.ui.BaseFragment;
import rm.com.disturb.ui.LoginFragment;
import rm.com.disturb.ui.MainActivity;
import rm.com.disturb.ui.NotifyFragment;
import rm.com.disturb.ui.PasswordDialogFragment;

/**
 * Created by alex
 */

@Singleton //
@Component(modules = {
    DisturbModule.class
}) //
public interface DisturbComponent {

  @Component.Builder //
  interface Builder {
    @BindsInstance Builder application(Application application);

    Builder disturbModule(DisturbModule disturbModule);

    DisturbComponent build();
  }

  void inject(MainActivity activity);

  void inject(BaseFragment fragment);

  void inject(NotifyFragment fragment);

  void inject(PasswordDialogFragment fragment);

  void inject(LoginFragment fragment);

  void inject(CallReceiver receiver);

  void inject(SmsReceiver receiver);
}
