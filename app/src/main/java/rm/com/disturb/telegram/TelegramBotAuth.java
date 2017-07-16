package rm.com.disturb.telegram;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import java.util.concurrent.ExecutorService;
import rm.com.disturb.async.AsyncResult;

/**
 * Created by alex
 */

public final class TelegramBotAuth implements Auth {
  private static final String MESSAGE_AUTH_TEST = "Authorized!";

  @NonNull private final ExecutorService executor;
  @NonNull private final TelegramBot bot;
  @NonNull private final Handler mainThreadHandler;

  public TelegramBotAuth(@NonNull ExecutorService executor, @NonNull TelegramBot bot,
      @NonNull Handler mainThreadHandler) {
    this.executor = executor;
    this.bot = bot;
    this.mainThreadHandler = mainThreadHandler;
  }

  @WorkerThread @Override //
  public boolean authorize(@NonNull String chatId) {
    final SendResponse response =
        bot.execute(new SendMessage(chatId, MESSAGE_AUTH_TEST).parseMode(ParseMode.Markdown));

    return response.isOk();
  }

  @Override public void authorizeAsync( //
      @NonNull final String chatId, //
      @NonNull final AsyncResult<Boolean> asyncResult //
  ) {
    executor.submit(new Runnable() {
      @Override public void run() {
        final boolean result = authorize(chatId);

        replyToMainThread(asyncResult, result);
      }
    });
  }

  private <T> void replyToMainThread( //
      @NonNull final AsyncResult<T> asyncResult, //
      final T result //
  ) {
    mainThreadHandler.post(new Runnable() {
      @Override public void run() {
        asyncResult.ready(result);
      }
    });
  }
}
