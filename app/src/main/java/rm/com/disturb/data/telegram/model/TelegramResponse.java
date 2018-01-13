package rm.com.disturb.data.telegram.model;

/**
 * Created by alex
 */

public final class TelegramResponse<T> {
  private boolean ok;
  private int error_code;
  private String description;
  private T result;

  public final T data() {
    return result;
  }

  public final boolean isOk() {
    return ok;
  }

  public final boolean isViable() {
    return isOk() && data() != null;
  }

  public final int errorCode() {
    return error_code;
  }

  public final String description() {
    return description;
  }
}
