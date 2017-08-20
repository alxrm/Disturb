package rm.com.disturb.inject;

import android.support.annotation.NonNull;
import dagger.Binds;
import dagger.Module;
import rm.com.disturb.command.Auth;
import rm.com.disturb.command.Erase;
import rm.com.disturb.command.Notify;
import rm.com.disturb.command.Update;
import rm.com.disturb.command.implementation.TelegramAuth;
import rm.com.disturb.command.implementation.TelegramErase;
import rm.com.disturb.command.implementation.TelegramNotify;
import rm.com.disturb.command.implementation.TelegramUpdate;

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
