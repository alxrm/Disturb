package rm.com.disturb.telegram;

/**
 * Created by alex
 */

public final class MessageResponse extends TelegramResponse {
  private Message result;

  public final Message message() {
    return result;
  }
}
