package rm.com.disturb.inject;

import android.app.Application;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import java.util.Arrays;
import javax.inject.Singleton;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.signal.CallAnsweredRule;
import rm.com.disturb.data.signal.CallFinishedRule;
import rm.com.disturb.data.signal.CallMissedRule;
import rm.com.disturb.data.signal.CallRingingRule;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.RuleSet;
import rm.com.disturb.data.signal.SignalRuleSet;
import rm.com.disturb.data.signal.SmsRule;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.inject.qualifier.Erase;
import rm.com.disturb.inject.qualifier.Notify;
import rm.com.disturb.inject.qualifier.Update;

/**
 * Created by alex
 */

@Module //
public final class RulesModule {

  private final Application application;

  public RulesModule(@NonNull Application application) {
    this.application = application;
  }

  @Provides @Singleton CallRingingRule provideCallRingingRule(
      @NonNull Storage<MessageSignal> signalStorage, @NonNull ContactBook contactBook,
      @NonNull @Notify Command<String> notify) {
    return new CallRingingRule(application, signalStorage, contactBook, notify);
  }

  @Provides @Singleton
  static CallAnsweredRule provideCallAnsweredRule(@NonNull Storage<MessageSignal> signalStorage,
      @NonNull @Update Command<String> update, @NonNull @Erase Command<Boolean> erase) {
    return new CallAnsweredRule(update, erase, signalStorage);
  }

  @Provides @Singleton
  static CallMissedRule provideCallMissedRule(@NonNull Storage<MessageSignal> signalStorage,
      @NonNull @Update Command<String> update) {
    return new CallMissedRule(update, signalStorage);
  }

  @Provides @Singleton
  static CallFinishedRule provideCallFinishedRule(@NonNull Storage<MessageSignal> signalStorage,
      @NonNull @Update Command<String> update, @NonNull @Erase Command<Boolean> erase) {
    return new CallFinishedRule(update, erase, signalStorage);
  }

  @Provides @Singleton SmsRule provideSmsRule(@NonNull ContactBook contactBook,
      @NonNull @Notify Command<String> notify) {
    return new SmsRule(contactBook, application, notify);
  }

  @Provides @Singleton
  static RuleSet<MessageSignal> provideRuleSet(@NonNull CallRingingRule callRingingRule,
      @NonNull CallMissedRule callMissedRule, @NonNull CallFinishedRule callFinishedRule,
      @NonNull CallAnsweredRule callAnsweredRule, @NonNull SmsRule smsRule) {
    return new SignalRuleSet(
        Arrays.asList(callRingingRule, callMissedRule, callFinishedRule, callAnsweredRule,
            smsRule));
  }
}
