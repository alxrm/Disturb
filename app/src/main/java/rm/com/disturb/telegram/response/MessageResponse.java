package rm.com.disturb.telegram.response;

import rm.com.disturb.telegram.model.Message;

/**
 * Created by alex
 */

public final class MessageResponse extends TelegramResponse {
  private Message result;

  public final Message message() {
    return result;
  }
}
