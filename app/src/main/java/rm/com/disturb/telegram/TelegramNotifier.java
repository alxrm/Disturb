package rm.com.disturb.telegram;

import android.support.annotation.NonNull;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.concurrent.ExecutorService;
import rm.com.disturb.storage.Preference;

/**
 * Created by alex
 */

public final class TelegramNotifier implements Notifier {
  @NonNull private final ExecutorService executor;
  @NonNull private final TelegramBot bot;
  @NonNull private final String chatId;

  public TelegramNotifier(@NonNull ExecutorService executor, @NonNull TelegramBot bot,
      @NonNull Preference<String> chatIdProvider) {
    this.executor = executor;
    this.bot = bot;
    this.chatId = chatIdProvider.get();
  }

  @Override public void notify(@NonNull final String message) {
    if (chatId.isEmpty()) {
      return;
    }

    executor.submit(new Runnable() {
      @Override public void run() {
        bot.execute(new SendMessage(chatId, message).parseMode(ParseMode.Markdown));
      }
    });
  }
}
