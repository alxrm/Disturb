package rm.com.disturb.data.telegram.response;

import java.util.Locale;
import rm.com.disturb.data.telegram.model.Message;

/**
 * Created by alex
 */

public final class MessageResponse extends TelegramResponse<Message> {
  @Override public String toString() {
    return String.format(Locale.US, "MessageResponse {\nok=%s;\nmessage=%d;\n}", isOk(),
        data().messageId());
  }
}
