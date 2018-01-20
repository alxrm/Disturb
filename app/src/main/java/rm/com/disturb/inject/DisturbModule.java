package rm.com.disturb.inject;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import io.paperdb.Book;
import io.paperdb.Paper;
import io.reactivex.schedulers.Schedulers;
import java.text.MessageFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rm.com.disturb.BuildConfig;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.rule.Rule;
import rm.com.disturb.data.signal.rule.RuleSet;
import rm.com.disturb.data.signal.rule.SignalRuleSet;
import rm.com.disturb.data.storage.BooleanPreference;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.storage.StringPreference;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.model.User;
import rm.com.disturb.data.telegram.source.Source;
import rm.com.disturb.data.telegram.source.UserSource;
import rm.com.disturb.inject.qualifier.HandleCalls;
import rm.com.disturb.inject.qualifier.ChatId;
import rm.com.disturb.inject.qualifier.Finished;
import rm.com.disturb.inject.qualifier.Magnify;
import rm.com.disturb.inject.qualifier.Missed;
import rm.com.disturb.inject.qualifier.Password;
import rm.com.disturb.inject.qualifier.Signals;
import rm.com.disturb.inject.qualifier.HandleSms;
import rm.com.disturb.inject.qualifier.Users;

import static android.content.Context.MODE_PRIVATE;
import static rm.com.disturb.data.telegram.TelegramApi.TELEGRAM_BASE_URL;

/**
 * Created by alex
 */
@Module(includes = {
    CommandModule.class, StorageModule.class, RulesModule.class, ResourceModule.class
}) //
public final class DisturbModule {
  private static final String PREFERENCES_NAME = "disturb";

  @Provides @Singleton SharedPreferences provideSharedPreferences(
      @NonNull Application application) {
    return application.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
  }

  @Provides @Singleton @ChatId
  static StringPreference provideChatIdPreference(@NonNull SharedPreferences preferences) {
    return new StringPreference(preferences, "chat-id");
  }

  @Provides @ChatId static String provideChatId(@ChatId StringPreference pref) {
    return pref.get();
  }

  @Provides @Singleton @Password
  static StringPreference providePasswordPreference(@NonNull SharedPreferences preferences) {
    return new StringPreference(preferences, "password");
  }

  @Provides @Password static String providePassword(@Password StringPreference pref) {
    return pref.get();
  }

  @Provides @Singleton @Missed
  static StringPreference provideMissedPreference(@NonNull SharedPreferences preferences) {
    return new StringPreference(preferences, "missed", "Edit message");
  }

  @Provides @Missed static String provideMissed(@Missed StringPreference pref) {
    return pref.get();
  }

  @Provides @Singleton @Finished
  static StringPreference provideFinishedPreference(@NonNull SharedPreferences preferences) {
    return new StringPreference(preferences, "finished", "Edit message");
  }

  @Provides @Finished static String provideFinished(@Finished StringPreference pref) {
    return pref.get();
  }

  @Provides @Singleton @Magnify
  static BooleanPreference provideMagnifyPreference(@NonNull SharedPreferences preferences) {
    return new BooleanPreference(preferences, "magnify", true);
  }

  @Provides @Magnify static boolean provideMagnify(@Magnify BooleanPreference pref) {
    return pref.get();
  }

  @Provides @Singleton @HandleCalls
  static BooleanPreference provideCallsPreference(@NonNull SharedPreferences preferences) {
    return new BooleanPreference(preferences, "calls", true);
  }

  @Provides @HandleCalls static boolean provideCalls(@HandleCalls BooleanPreference pref) {
    return pref.get();
  }

  @Provides @Singleton @HandleSms
  static BooleanPreference provideSmsPreference(@NonNull SharedPreferences preferences) {
    return new BooleanPreference(preferences, "sms", true);
  }

  @Provides @HandleSms static boolean provideSms(@HandleSms BooleanPreference pref) {
    return pref.get();
  }

  @Provides @Singleton static OkHttpClient provideHttpClient() {
    return new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build();
  }

  @Provides @Singleton static TelegramApi provideRetrofit(@NonNull OkHttpClient httpClient) {
    return new Retrofit.Builder().client(httpClient)
        .baseUrl(MessageFormat.format("{0}{1}/", TELEGRAM_BASE_URL, BuildConfig.BOT_TOKEN))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .client(httpClient)
        .build()
        .create(TelegramApi.class);
  }

  @Provides @Singleton @Signals Book provideSignalsDatabase() {
    return Paper.book("signals");
  }

  @Provides @Singleton @Users Book provideUsersDatabase() {
    return Paper.book("users");
  }

  @Provides @Singleton static Source<User, String> provideUserSource(@NonNull TelegramApi api,
      @NonNull Storage<User> userStorage) {
    return new UserSource(api, userStorage);
  }

  @Provides @Singleton
  static RuleSet<MessageSignal> provideRuleSet(@NonNull Set<Rule<MessageSignal>> rules) {
    return new SignalRuleSet(rules);
  }
}
