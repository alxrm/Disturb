package rm.com.disturb.inject;

import android.support.annotation.NonNull;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;
import rm.com.disturb.data.signal.rule.CallAnsweredRule;
import rm.com.disturb.data.signal.rule.CallFinishedRule;
import rm.com.disturb.data.signal.rule.CallMissedRule;
import rm.com.disturb.data.signal.rule.CallRingingRule;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.rule.Rule;
import rm.com.disturb.data.signal.rule.SmsRule;

/**
 * Created by alex
 */

@Module //
public abstract class RulesModule {

  @Binds @IntoSet //
  abstract Rule<MessageSignal> provideCallRingingRule(@NonNull CallRingingRule rule);

  @Binds @IntoSet //
  abstract Rule<MessageSignal> provideCallFinishedRule(@NonNull CallFinishedRule rule);

  @Binds @IntoSet //
  abstract Rule<MessageSignal> provideCallMissedRule(@NonNull CallMissedRule rule);

  @Binds @IntoSet //
  abstract Rule<MessageSignal> provideCallAnsweredRule(@NonNull CallAnsweredRule rule);

  @Binds @IntoSet //
  abstract Rule<MessageSignal> provideSmsgRule(@NonNull SmsRule rule);
}
