package rm.com.disturb.telegram;

/**
 * Created by alex
 */

class TelegramResponse {
  private boolean ok;
  private int error_code;
  private String description;

  public final boolean isOk() {
    return ok;
  }

  public final int errorCode() {
    return error_code;
  }

  public final String description() {
    return description;
  }
}
