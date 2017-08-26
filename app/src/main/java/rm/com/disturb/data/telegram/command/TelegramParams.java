package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import rm.com.disturb.data.telegram.TelegramApi;

public final class TelegramParams {
  private final Map<String, String> paramsMap;

  private TelegramParams(@NonNull Builder builder) {
    paramsMap = Collections.unmodifiableMap(builder.paramsMap);
  }

  @NonNull public static TelegramParams ofMessage(@NonNull String text) {
    return new TelegramParams.Builder().text(text).build();
  }

  @NonNull public static TelegramParams ofMessageId(@NonNull String messageId) {
    return new TelegramParams.Builder().messageId(messageId).build();
  }

  @NonNull public static TelegramParams ofChatId(@NonNull String chatId) {
    return new TelegramParams.Builder().chatId(chatId).build();
  }

  @NonNull public Map<String, String> asMap() {
    return paramsMap;
  }

  @NonNull public Builder newBuilder() {
    return new Builder(this);
  }

  public static final class Builder {
    final Map<String, String> paramsMap;

    public Builder() {
      paramsMap = new HashMap<>(3);
    }

    public Builder(@NonNull TelegramParams params) {
      paramsMap = new HashMap<>(params.paramsMap);
    }

    @NonNull public Builder chatId(@NonNull String value) {
      paramsMap.put(TelegramApi.KEY_CHAT_ID, value);
      return this;
    }

    @NonNull public Builder text(@NonNull String value) {
      paramsMap.put(TelegramApi.KEY_TEXT, value);
      return this;
    }

    @NonNull public Builder messageId(@NonNull String value) {
      paramsMap.put(TelegramApi.KEY_MESSAGE_ID, value);
      return this;
    }

    @NonNull public TelegramParams build() {
      return new TelegramParams(this);
    }
  }
}
