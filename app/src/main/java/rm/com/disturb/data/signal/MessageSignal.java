package rm.com.disturb.data.signal;

import android.support.annotation.NonNull;

public final class MessageSignal implements Signal<String> {

  private final @NonNull String type;
  private final @NonNull String data;
  private final @NonNull String key;
  private final long time;

  private MessageSignal(@NonNull Builder builder) {
    this.type = builder.type;
    this.data = builder.data;
    this.time = builder.time;
    this.key = builder.key;
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

  public static final class Builder {
    String type;
    String data;
    String key;
    long time;

    public Builder() {
      type = Signals.EMPTY;
      data = Signals.EMPTY;
      key = Signals.EMPTY;
      time = System.nanoTime();
    }

    Builder(@NonNull MessageSignal current) {
      type = current.type;
      data = current.data;
      key = current.key;
      time = current.time;
    }

    @NonNull public Builder type(@NonNull String type) {
      this.type = type;
      return this;
    }

    @NonNull public Builder data(@NonNull String data) {
      this.data = data;
      return this;
    }

    @NonNull public Builder key(@NonNull String key) {
      this.key = key;
      return this;
    }

    @NonNull public Builder time(long time) {
      this.time = time;
      return this;
    }

    @NonNull public MessageSignal build() {
      return new MessageSignal(this);
    }
  }
}
