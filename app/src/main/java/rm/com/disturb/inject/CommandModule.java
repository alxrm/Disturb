package rm.com.disturb.inject;

import android.support.annotation.NonNull;
import dagger.Binds;
import dagger.Module;
import rm.com.disturb.data.telegram.command.Command;
import rm.com.disturb.data.telegram.command.TelegramAuth;
import rm.com.disturb.data.telegram.command.TelegramErase;
import rm.com.disturb.data.telegram.command.TelegramNotify;
import rm.com.disturb.data.telegram.command.TelegramUpdate;
import rm.com.disturb.inject.qualifier.Auth;
import rm.com.disturb.inject.qualifier.Erase;
import rm.com.disturb.inject.qualifier.Notify;
import rm.com.disturb.inject.qualifier.Update;

/**
 * Created by alex
 */

@Module //
public abstract class CommandModule {

  @Binds @Auth abstract Command<Boolean> bindAuth(@NonNull TelegramAuth telegramAuth);

  @Binds @Notify abstract Command<String> bindNotify(@NonNull TelegramNotify telegramNotify);

  @Binds @Erase abstract Command<Boolean> bindErase(@NonNull TelegramErase telegramErase);

  @Binds @Update abstract Command<String> bindUpdate(@NonNull TelegramUpdate telegramUpdate);
}
