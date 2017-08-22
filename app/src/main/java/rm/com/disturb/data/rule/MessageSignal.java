package rm.com.disturb.data.rule;

import android.support.annotation.NonNull;

public final class MessageSignal implements Signal<String> {

  private final @NonNull String type;
  private final @NonNull String data;
  private final @NonNull String key;
  private final long time;

  public MessageSignal(@NonNull String type, @NonNull String data, @NonNull String key) {
    this.type = type;
    this.data = data;
    this.time = System.nanoTime();
    this.key = key;
  }

  @NonNull @Override public String type() {
    return type;
  }

  @NonNull @Override public String data() {
    return data;
  }

  @Override public long time() {
    return time;
  }

  @NonNull @Override public String key() {
    return key;
  }
}
