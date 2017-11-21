package rm.com.disturb.data.resource;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import rm.com.disturb.data.async.PendingResult;

import static android.provider.BaseColumns._ID;
import static android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI;
import static android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME;

/**
 * Created by alex
 */
public final class ContactResource implements Resource<String, String> {
  private static final String EMPTY_NAME = "";

  private final PendingResult<String> result;

  public ContactResource(@NonNull ExecutorService executor, @NonNull Handler handler) {
    this.result = new PendingResult.Builder<>(EMPTY_NAME) //
        .executor(executor) //
        .handler(handler) //
        .build();
  }

  @Override public PendingResult<String> load(@NonNull Context context, @NonNull String phone) {
    return result.newBuilder().from(findNameCallable(context.getContentResolver(), phone)).build();
  }

  @NonNull private String findNameBlocking(@NonNull ContentResolver contentResolver,
      @NonNull String phoneNumber) {
    final Uri uri = Uri.withAppendedPath(CONTENT_FILTER_URI, Uri.encode(phoneNumber));
    final Cursor contactLookup = contentResolver.query(uri, new String[] {
        _ID, DISPLAY_NAME
    }, null, null, null);

    if (contactLookup == null) {
      return EMPTY_NAME;
    }

    try {
      if (contactLookup.getCount() > 0) {
        contactLookup.moveToNext();
        final int columnIndex = contactLookup.getColumnIndex(DISPLAY_NAME);
        final String name = contactLookup.getString(columnIndex);

        return name == null ? EMPTY_NAME : name;
      }
    } finally {
      contactLookup.close();
    }

    return EMPTY_NAME;
  }

  @NonNull private Callable<String> findNameCallable(@NonNull final ContentResolver contentResolver,
      @NonNull final String phoneNumber) {
    return () -> findNameBlocking(contentResolver, phoneNumber);
  }
}
