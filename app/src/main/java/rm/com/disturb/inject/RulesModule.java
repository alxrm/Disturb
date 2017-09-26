package rm.com.disturb.inject;

import android.app.Application;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import java.util.Arrays;
import javax.inject.Singleton;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.signal.CallRingingRule;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.RuleSet;
import rm.com.disturb.data.signal.SignalRuleSet;
import rm.com.disturb.data.signal.SmsRule;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.inject.qualifier.Notify;

/**
 * Created by alex
 */

@Module //
public final class RulesModule {

  private final Application application;

  public RulesModule(@NonNull Application application) {
    this.application = application;
  }

  @Provides @Singleton CallRingingRule provideCallRule(
      @NonNull Storage<MessageSignal> signalStorage, @NonNull ContactBook contactBook,
      @NonNull @Notify Command<String> notify) {
    return new CallRingingRule(application, signalStorage, contactBook, notify);
  }

  @Provides @Singleton SmsRule provideSmsRule(@NonNull ContactBook contactBook,
      @NonNull @Notify Command<String> notify) {
    return new SmsRule(contactBook, application, notify);
  }

  @Provides @Singleton
  static RuleSet<MessageSignal> provideRuleSet(@NonNull CallRingingRule callRule,
      @NonNull SmsRule smsRule) {
    return new SignalRuleSet(Arrays.asList(callRule, smsRule));
  }
}
