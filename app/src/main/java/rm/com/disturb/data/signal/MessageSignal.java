package rm.com.disturb.data.signal;

import android.support.annotation.NonNull;

public final class MessageSignal {
  public static final MessageSignal EMPTY_MESSAGE = new MessageSignal.Builder().build();

  private final String type;
  private final String data;
  private final String phone;
  private final long time;

  private final String sender;
  private final String remoteKey;

  private MessageSignal(@NonNull Builder builder) {
    this.type = builder.type;
    this.data = builder.data;
    this.time = builder.time;
    this.phone = builder.phone;
    this.remoteKey = builder.remoteKey;
    this.sender = builder.sender;
  }

  @NonNull public String type() {
    return type;
  }

  @NonNull public String data() {
    return data;
  }

  public long time() {
    return time;
  }

  @NonNull public String key() {
    return phone;
  }

  @NonNull public String remoteKey() {
    return remoteKey;
  }

  @NonNull public String sender() {
    return sender;
  }

  @NonNull public Builder newBuilder() {
    return new Builder(this);
  }

  public static final class Builder {
    private String type;
    private String data;
    private String phone;
    private long time;

    private String remoteKey;
    private String sender;

    public Builder() {
      type = Signals.EMPTY;
      data = Signals.EMPTY;
      phone = Signals.EMPTY;
      remoteKey = Signals.EMPTY;
      sender = Signals.EMPTY;
      time = System.nanoTime();
    }

    Builder(@NonNull MessageSignal current) {
      type = current.type;
      data = current.data;
      phone = current.phone;
      time = current.time;
      remoteKey = current.remoteKey;
      sender = current.sender;
    }

    @NonNull public Builder type(@NonNull String type) {
      this.type = type;
      return this;
    }

    @NonNull public Builder data(@NonNull String data) {
      this.data = data;
      return this;
    }

    @NonNull public Builder phone(@NonNull String key) {
      this.phone = key;
      return this;
    }

    @NonNull public Builder time(long time) {
      this.time = time;
      return this;
    }

    @NonNull public Builder remoteKey(@NonNull String remoteKey) {
      this.remoteKey = remoteKey;
      return this;
    }

    @NonNull public Builder sender(@NonNull String sender) {
      this.sender = sender;
      return this;
    }

    @NonNull public MessageSignal build() {
      return new MessageSignal(this);
    }
  }
}
