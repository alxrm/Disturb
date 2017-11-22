package rm.com.disturb.inject;

import android.support.annotation.NonNull;
import dagger.Binds;
import dagger.Module;
import rm.com.disturb.data.signal.MessageSignal;
import rm.com.disturb.data.storage.PaperSignalStorage;
import rm.com.disturb.data.storage.PaperUserStorage;
import rm.com.disturb.data.storage.Storage;
import rm.com.disturb.data.telegram.model.User;

/**
 * Created by alex
 */

@Module //
public abstract class StorageModule {

  @Binds abstract Storage<User> bindUserStorage(@NonNull PaperUserStorage database);

  @Binds abstract Storage<MessageSignal> bindSignalStorage(@NonNull PaperSignalStorage database);
}