package rm.com.disturb;

import android.app.Application;
import android.support.annotation.NonNull;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;

/**
 * Created by alex
 */
@Module final class DisturbModule {

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

  @Provides @Singleton static Notifier provideTelegramNotifier(@NonNull ExecutorService executor,
      @NonNull TelegramBot bot) {
    return new TelegramNotifier(executor, bot, BuildConfig.CHAT_ID);
  }

  @Provides @Singleton ContactBook provideAsyncContactBook(@NonNull ExecutorService executor) {
    return new LocalContactBook(executor, application.getApplicationContext());
  }
}
