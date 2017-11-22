package rm.com.disturb.data.telegram.command;

import android.support.annotation.NonNull;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import rm.com.disturb.data.telegram.TelegramApi;

/**
 * Created by alex
 */

public abstract class AbstractTelegramCommand<T> implements TelegramCommand<T> {
  final TelegramApi api;

  public AbstractTelegramCommand(@NonNull TelegramApi api) {
    this.api = api;
  }

  @NonNull @Override public Flowable<T> send(@NonNull TelegramParams params) {
    return Flowable.fromCallable(() -> sendBlocking(params))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.single());
  }

  @NonNull abstract T sendBlocking(@NonNull TelegramParams params) throws IOException;
}
