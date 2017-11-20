package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.paperdb.Book;
import rm.com.disturb.data.telegram.model.UserFull;

/**
 * Created by alex
 */

public class PaperUserStorage extends PaperStorage<UserFull> {
  public PaperUserStorage(@NonNull Book database) {
    super(database);
  }

  @Override public void put(@NonNull String key, @Nullable UserFull value) {
    database.destroy();
    super.put(key, value);
  }
}
