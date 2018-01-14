package rm.com.disturb.inject;

import android.support.annotation.NonNull;
import dagger.Binds;
import dagger.Module;
import java8.util.Optional;
import rm.com.disturb.data.telegram.command.TelegramAuth;
import rm.com.disturb.data.telegram.command.TelegramCommand;
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

  @Binds @Auth //
  abstract TelegramCommand<Boolean> bindAuth(@NonNull TelegramAuth telegramAuth);

  @Binds @Notify
  abstract TelegramCommand<Optional<String>> bindNotify(@NonNull TelegramNotify telegramNotify);

  @Binds @Erase //
  abstract TelegramCommand<Boolean> bindErase(@NonNull TelegramErase telegramErase);

  @Binds @Update
  abstract TelegramCommand<Optional<String>> bindUpdate(@NonNull TelegramUpdate telegramUpdate);
}
