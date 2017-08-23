package rm.com.disturb.inject;

import android.support.annotation.NonNull;
import dagger.Binds;
import rm.com.disturb.data.rule.MessageSignal;
import rm.com.disturb.data.storage.SignalStorage;
import rm.com.disturb.data.storage.Storage;

/**
 * Created by alex
 */

public abstract class StorageModule {
  @Binds abstract Storage<MessageSignal> bindAuth(@NonNull SignalStorage signalStorage);
}
