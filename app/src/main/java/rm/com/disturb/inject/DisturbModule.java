package rm.com.disturb.inject;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rm.com.disturb.BuildConfig;
import rm.com.disturb.data.contact.ContactBook;
import rm.com.disturb.data.contact.LocalContactBook;
import rm.com.disturb.data.signal.CallRingingRule;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.signal.RuleSet;
import rm.com.disturb.data.signal.SignalRuleSet;
import rm.com.disturb.data.signal.SmsRule;
import rm.com.disturb.data.storage.SignalStorage;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.storage.StringPreference;
import rm.com.disturb.data.telegram.TelegramApi;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.inject.qualifier.ChatId;
import rm.com.disturb.inject.qualifier.Notify;
import rm.com.disturb.inject.qualifier.Password;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alex
 */
@Module(includes = { CommandModule.class }) //
public final class DisturbModule {
  private static final String PREFERENCES_NAME = "disturb";
  private static final String TELEGRAM_BASE_URL = "https://api.telegram.org/bot";

  private final Application application;

  public DisturbModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton static ExecutorService provideExecutorService() {
    return Executors.newSingleThreadScheduledExecutor();
  }

  @Provides @Singleton static Handler provideMainThreadHandler() {
    return new Handler(Looper.getMainLooper());
  }

  @Provides @Singleton SharedPreferences provideSharedPreferences() {
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

  @Provides @Singleton ContactBook provideAsyncContactBook(@NonNull ExecutorService executor) {
    return new LocalContactBook(executor, application.getContentResolver());
  }

  @Provides @Singleton static OkHttpClient provideHttpClient() {
    return new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build();
  }

  @Provides @Singleton static Retrofit provideRetrofit(@NonNull OkHttpClient httpClient) {
    return new Retrofit.Builder().client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(MessageFormat.format("{0}{1}/", TELEGRAM_BASE_URL, BuildConfig.BOT_TOKEN))
        .client(httpClient)
        .build();
  }

  @Provides @Singleton static TelegramApi provideApi(@NonNull Retrofit retrofit) {
    return retrofit.create(TelegramApi.class);
  }

  @Provides @Singleton Storage<MessageSignal> provideSignalStorage() {
    return new SignalStorage(application);
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
