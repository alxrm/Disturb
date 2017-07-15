package rm.com.disturb;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;
import rm.com.disturb.contact.ContactBook;
import rm.com.disturb.contact.LocalContactBook;
import rm.com.disturb.storage.ChatId;
import rm.com.disturb.storage.Password;
import rm.com.disturb.storage.StringPreference;
import rm.com.disturb.telegram.Notifier;
import rm.com.disturb.telegram.TelegramNotifier;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by alex
 */
@Module //
final class DisturbModule {
  private static final String PREFERENCES_NAME = "disturb";

  private final Application application;

  DisturbModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton static ExecutorService provideExecutorService() {
    return Executors.newSingleThreadScheduledExecutor();
  }

  @Provides @Singleton static TelegramBot provideTelegramBot() {
    return TelegramBotAdapter.build(BuildConfig.BOT_TOKEN);
  }

  @Provides @Singleton SharedPreferences provideSharedPreferences() {
    return application.getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
  }

  @Provides @Singleton static Notifier provideTelegramNotifier(@NonNull ExecutorService executor,
      @NonNull TelegramBot bot, @NonNull @ChatId StringPreference chatIdPreference) {
    return new TelegramNotifier(executor, bot, chatIdPreference);
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

  @Provides @Password static String providePassword(@ChatId StringPreference pref) {
    return pref.get();
  }

  @Provides @Singleton ContactBook provideAsyncContactBook(@NonNull ExecutorService executor) {
    return new LocalContactBook(executor, application.getApplicationContext());
  }
}
