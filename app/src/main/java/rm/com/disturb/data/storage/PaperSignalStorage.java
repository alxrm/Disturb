package rm.com.disturb.data.storage;

import android.support.annotation.NonNull;
import io.paperdb.Book;
import rm.com.disturb.data.signal.MessageSignal;

/**
 * Created by alex
 */

public final class PaperSignalStorage extends PaperStorage<MessageSignal> {
  public PaperSignalStorage(@NonNull Book database) {
    super(database);
  }
}
