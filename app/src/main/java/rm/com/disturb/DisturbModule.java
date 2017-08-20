package rm.com.disturb;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.inject.Provider;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rm.com.disturb.contact.ContactBook;
import rm.com.disturb.contact.LocalContactBook;
import rm.com.disturb.storage.ChatId;
import rm.com.disturb.storage.Password;
import rm.com.disturb.storage.StringPreference;
import rm.com.disturb.telegram.Auth;
import rm.com.disturb.telegram.Notify;
import rm.com.disturb.telegram.TelegramApi;
import rm.com.disturb.telegram.impl.TelegramAuth;
import rm.com.disturb.telegram.impl.TelegramNotify;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alex
 */
@Module //
final class DisturbModule {
  private static final String PREFERENCES_NAME = "disturb";
  private static final String TELEGRAM_BASE_URL = "https://api.telegram.org/bot";

  private final Application application;

  DisturbModule(Application application) {
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

  @Provides @Singleton static Notify provideTelegramNotifier(@NonNull ExecutorService executor,
      @NonNull Handler mainThreadHandler, @NonNull TelegramApi api,
      @NonNull @ChatId Provider<String> chatId) {
    return new TelegramNotify(executor, mainThreadHandler, api, chatId);
  }

  @Provides @Singleton static Auth provideTelegramBotAuth(@NonNull ExecutorService executor,
      @NonNull Handler mainThreadHandler, @NonNull TelegramApi api) {
    return new TelegramAuth(executor, mainThreadHandler, api);
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

  @Provides @Singleton OkHttpClient provideHttpClient() {
    return new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build();
  }

  @Provides @Singleton Retrofit provideRetrofit(@NonNull OkHttpClient httpClient) {
    return new Retrofit.Builder().client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(MessageFormat.format("{0}{1}/", TELEGRAM_BASE_URL, BuildConfig.BOT_TOKEN))
        .client(httpClient)
        .build();
  }

  @Provides @Singleton TelegramApi provideApi(@NonNull Retrofit retrofit) {
    return retrofit.create(TelegramApi.class);
  }
}
