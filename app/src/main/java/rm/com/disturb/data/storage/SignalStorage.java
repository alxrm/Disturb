package rm.com.disturb.data.storage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import javax.inject.Singleton;
import rm.com.disturb.data.signal.MessageSignal;

/**
 * Created by alex
 */

@Singleton //
public final class SignalStorage implements Storage<MessageSignal> {

  private final @NonNull Context context;

  public SignalStorage(@NonNull Context context) {
    this.context = context.getApplicationContext();
  }

  @Override public void put(@NonNull final String key, @Nullable final MessageSignal value) {
    
  }

  @Nullable @Override public MessageSignal get(@NonNull final String key) {
    return null;
  }

  @Override public boolean contains(@NonNull final String key) {
    return false;
  }

  @NonNull @Override public List<MessageSignal> all() {
    return Collections.emptyList();
  }
}
