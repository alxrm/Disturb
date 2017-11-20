package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.paperdb.Book;
import java.util.ArrayList;
import java.util.List;
import rm.com.disturb.utils.Lists;

/**
 * Created by alex
 */

public class PaperStorage<T> implements Storage<T> {

  final Book database;

  public PaperStorage(@NonNull Book database) {
    this.database = database;
  }

  @Override public void put(@NonNull String key, @Nullable T value) {
    if (value != null) {
      database.write(key, value);
    } else {
      database.delete(key);
    }
  }

  @Nullable @Override public T get(@NonNull String key) {
    return database.read(key);
  }

  @NonNull @Override public T get(@NonNull String key, @NonNull T defaultValue) {
    return database.read(key, defaultValue);
  }

  @Override public void delete(@NonNull String key) {
    database.delete(key);
  }

  @Override public boolean contains(@NonNull String key) {
    return database.contains(key);
  }

  @NonNull @Override public List<T> all() {
    final List<String> allKeys = database.getAllKeys();
    final List<T> signals = new ArrayList<>(allKeys.size());

    return Lists.reduce(allKeys, signals, new Lists.Accumulator<String, List<T>>() {
      @Override public List<T> collect(List<T> result, String item) {
        if (database.contains(item)) {
          result.add(get(item));
        }

        return result;
      }
    });
  }

  @Override public void clear() {
    database.destroy();
  }
}
