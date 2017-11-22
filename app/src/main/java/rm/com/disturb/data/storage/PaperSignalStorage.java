package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import io.paperdb.Book;
import javax.inject.Inject;
import javax.inject.Singleton;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.inject.qualifier.Signals;

/**
 * Created by alex
 */

@Singleton //
public final class PaperSignalStorage extends PaperStorage<MessageSignal> {
  @Inject PaperSignalStorage(@NonNull @Signals Book database) {
    super(database);
  }
}
