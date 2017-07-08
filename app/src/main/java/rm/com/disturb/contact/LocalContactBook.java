package rm.com.disturb.contact;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import java.util.concurrent.ExecutorService;
import rm.com.disturb.utils.AsyncResult;

/**
 * Created by alex
 */
public final class LocalContactBook implements ContactBook {
  @NonNull private final ExecutorService executor;
  @NonNull private final Context context;

  public LocalContactBook(@NonNull ExecutorService executor, @NonNull Context context) {
    this.executor = executor;
    this.context = context;
  }

  @Override public void findNameAsync(@NonNull final String phoneNumber,
      @NonNull final AsyncResult<String> resultHook) {
    executor.submit(new Runnable() {
      @Override public void run() {
        resultHook.ready(findName(phoneNumber));
      }
    });
  }

  @NonNull @Override public String findName(@NonNull String phoneNumber) {
    final Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
        Uri.encode(phoneNumber));

    final ContentResolver contentResolver = context.getContentResolver();
    final Cursor contactLookup = contentResolver.query(uri, new String[] {
        BaseColumns._ID, ContactsContract.PhoneLookup.DISPLAY_NAME
    }, null, null, null);

    String name = "";

    try {
      if (contactLookup != null && contactLookup.getCount() > 0) {
        contactLookup.moveToNext();
        name = contactLookup.getString(
            contactLookup.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
      }
    } finally {
      if (contactLookup != null) {
        contactLookup.close();
      }
    }

    return name;
  }
}
