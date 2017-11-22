package rm.com.disturb.data.resource;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import java.util.concurrent.ExecutorService;
import java8.util.Optional;
import rm.com.disturb.data.async.PendingResult;

import static android.provider.BaseColumns._ID;
import static android.provider.ContactsContract.PhoneLookup.CONTENT_FILTER_URI;
import static android.provider.ContactsContract.PhoneLookup.DISPLAY_NAME;

/**
 * Created by alex
 */
public final class ContactResource implements Resource<String, String> {
  private final PendingResult<String> result;

  public ContactResource(@NonNull ExecutorService executor, @NonNull Handler handler) {
    this.result = new PendingResult.Builder<String>() //
        .executor(executor) //
        .handler(handler) //
        .build();
  }

  @Override public PendingResult<String> load(@NonNull Context context, @NonNull String phone) {
    return result.newBuilder()
        .from(() -> findNameBlocking(context.getContentResolver(), phone))
        .build();
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
