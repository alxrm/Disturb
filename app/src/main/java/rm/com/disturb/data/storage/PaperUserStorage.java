package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import io.paperdb.Book;
import javax.inject.Inject;
import javax.inject.Singleton;
import rm.com.disturb.data.telegram.model.User;
import rm.com.disturb.inject.qualifier.Users;

/**
 * Created by alex
 */

@Singleton //
public class PaperUserStorage extends PaperStorage<User> {
  @Inject PaperUserStorage(@NonNull @Users Book database) {
    super(database);
  }

  @Override public void put(@NonNull String key, @Nullable User value) {
    database.destroy();
    super.put(key, value);
  }
}
