package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.paperdb.Book;
import rm.com.disturb.data.telegram.model.User;

/**
 * Created by alex
 */

public class PaperUserStorage extends PaperStorage<User> {
  public PaperUserStorage(@NonNull Book database) {
    super(database);
  }

  @Override public void put(@NonNull String key, @Nullable User value) {
    database.destroy();
    super.put(key, value);
  }
}
