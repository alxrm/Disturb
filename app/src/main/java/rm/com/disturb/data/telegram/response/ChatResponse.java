package rm.com.disturb.data.telegram.response;

import java.util.Locale;
import rm.com.disturb.data.telegram.model.Chat;

/**
 * Created by alex
 */

public final class ChatResponse extends TelegramResponse<Chat> {
  @Override public String toString() {
    return String.format(Locale.US, "MessageResponse {\nok=%s;\nchatId=%s;\nfirstName=%s\n}",
        isOk(), data().id(), data().firstName());
  }
}
