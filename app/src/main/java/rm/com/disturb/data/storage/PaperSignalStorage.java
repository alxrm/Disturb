package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.paperdb.Book;
import java.util.ArrayList;
import java.util.List;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.utils.Lists;
import rm.com.disturb.utils.Lists.Accumulator;

/**
 * Created by alex
 */

public final class PaperSignalStorage implements Storage<MessageSignal> {
  private final Book database;

  public PaperSignalStorage(@NonNull Book database) {
    this.database = database;
  }

  @Override public void put(@NonNull String key, @Nullable MessageSignal value) {
    if (value != null) {
      database.write(key, value);
    } else {
      database.delete(key);
    }
  }

  @NonNull @Override public MessageSignal get(@NonNull String key) {
    return database.read(key, new MessageSignal.Builder().build());
  }

  @Override public void delete(@NonNull String key) {
    database.delete(key);
  }

  @Override public boolean contains(@NonNull String key) {
    return database.exist(key);
  }

  @NonNull @Override public List<MessageSignal> all() {
    final List<String> allKeys = database.getAllKeys();
    final List<MessageSignal> signals = new ArrayList<>(allKeys.size());

    return Lists.reduce(allKeys, signals, new Accumulator<String, List<MessageSignal>>() {
      @Override public List<MessageSignal> collect(List<MessageSignal> result, String item) {
        result.add(get(item));
        return result;
      }
    });
  }

  @Override public void clear() {
    database.destroy();
  }
}
