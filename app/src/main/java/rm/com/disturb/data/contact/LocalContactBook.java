package rm.com.disturb.data.contact;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
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
public final class LocalContactBook implements ContactBook {
  private static final String EMPTY_NAME = "";

  private final ContentResolver contentResolver;
  private final PendingResult<String> result;

  public LocalContactBook(@NonNull ExecutorService executor,
      @NonNull ContentResolver contentResolver) {
    this.contentResolver = contentResolver;
    this.result = new PendingResult.Builder<>(EMPTY_NAME).executor(executor).build();
  }

  @NonNull @Override public PendingResult<String> findName(@NonNull String phoneNumber) {
    return result.newBuilder().from(findNameCallable(phoneNumber)).build();
  }

  @NonNull private String findNameBlocking(@NonNull String phoneNumber) {
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

  @NonNull private Callable<String> findNameCallable(@NonNull final String phoneNumber) {
    return new Callable<String>() {
      @Override public String call() throws Exception {
        return findNameBlocking(phoneNumber);
      }
    };
  }
}
