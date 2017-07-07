package rm.com.disturb;

import android.support.annotation.NonNull;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import java.util.concurrent.ExecutorService;

/**
 * Created by alex
 */

final class TelegramNotifier implements Notifier {

  @NonNull private final ExecutorService executor;
  @NonNull private final TelegramBot bot;
  @NonNull private final String chatId;

  TelegramNotifier(@NonNull ExecutorService executor, @NonNull TelegramBot bot,
      @NonNull String chatId) {
    this.executor = executor;
    this.bot = bot;
    this.chatId = chatId;
  }

  @Override public void notify(@NonNull final String message) {
    executor.submit(new Runnable() {
      @Override public void run() {
        bot.execute(new SendMessage(chatId, message).parseMode(ParseMode.Markdown));
      }
    });
  }
}
