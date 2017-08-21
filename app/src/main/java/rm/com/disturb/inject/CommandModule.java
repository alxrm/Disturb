package rm.com.disturb.inject;

import android.support.annotation.NonNull;
import dagger.Binds;
import dagger.Module;
import rm.com.disturb.data.command.Auth;
import rm.com.disturb.data.command.Erase;
import rm.com.disturb.data.command.Notify;
import rm.com.disturb.data.command.Update;
import rm.com.disturb.data.command.implementation.TelegramAuth;
import rm.com.disturb.data.command.implementation.TelegramErase;
import rm.com.disturb.data.command.implementation.TelegramNotify;
import rm.com.disturb.data.command.implementation.TelegramUpdate;

/**
 * Created by alex
 */

@Module //
public abstract class CommandModule {

  @Binds abstract Auth bindAuth(@NonNull TelegramAuth telegramAuth);

  @Binds abstract Notify bindNotify(@NonNull TelegramNotify telegramNotify);

  @Binds abstract Erase bindErase(@NonNull TelegramErase telegramErase);

  @Binds abstract Update bindUpdate(@NonNull TelegramUpdate telegramUpdate);
}
