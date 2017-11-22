package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.paperdb.Book;
import java.util.ArrayList;
import java.util.List;
import java8.util.Optional;
import java8.util.stream.StreamSupport;

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

  @NonNull @Override public Optional<T> get(@NonNull String key) {
    return Optional.ofNullable(database.read(key));
  }

  @Override public void delete(@NonNull String key) {
    database.delete(key);
  }

  @Override public boolean contains(@NonNull String key) {
    return database.contains(key);
  }

  @NonNull @Override public List<T> all() {
    return StreamSupport.stream(database.getAllKeys()) //
        .collect( //
            ArrayList::new, //
            (result, key) -> get(key).ifPresent(result::add), //
            ArrayList::addAll //
        );
  }

  @Override public void clear() {
    database.destroy();
  }
}
