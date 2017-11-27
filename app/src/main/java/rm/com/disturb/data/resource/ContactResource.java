package rm.com.disturb.data.resource;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java8.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

import static android.provider.BaseColumns._ID;
import static android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI;
import static android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME;

/**
 * Created by alex
 */

@Singleton //
public final class ContactResource implements Resource<String, String> {

  @Inject ContactResource() {
  }

  @NonNull @Override
  public Single<Optional<String>> load(@NonNull Context context, @NonNull String phone) {
    return Single.fromCallable(() -> findNameBlocking(context.getContentResolver(), phone))
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.single());
  }

  @NonNull private Optional<String> findNameBlocking(@NonNull ContentResolver contentResolver,
      @NonNull String phoneNumber) {
    final Uri uri = Uri.withAppendedPath(CONTENT_FILTER_URI, Uri.encode(phoneNumber));
    final Cursor contactLookup = contentResolver.query(uri, new String[] {
        _ID, DISPLAY_NAME
    }, null, null, null);

    if (contactLookup == null) {
      return Optional.empty();
    }

    try {
      if (contactLookup.getCount() > 0) {
        contactLookup.moveToNext();
        final int columnIndex = contactLookup.getColumnIndex(DISPLAY_NAME);
        final String name = contactLookup.getString(columnIndex);

        return Optional.ofNullable(name);
      }
    } finally {
      contactLookup.close();
    }

    return Optional.empty();
  }
}
