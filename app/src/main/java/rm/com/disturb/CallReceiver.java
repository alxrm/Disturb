package rm.com.disturb;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.telephony.TelephonyManager;
import java.util.concurrent.ExecutorService;
import javax.inject.Inject;

/**
 * Created by alex
 */
public final class CallReceiver extends BroadcastReceiver {

  @Inject Notifier notifier;
  @Inject ExecutorService executor;

  @Override public void onReceive(Context context, Intent intent) {
    ((DisturbApplication) context.getApplicationContext()).injector().inject(this);

    if (!Intents.matches(intent, TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
      return;
    }

    final String state = intent.getExtras()
        .getString(TelephonyManager.EXTRA_STATE, TelephonyManager.EXTRA_STATE_IDLE);

    final String number =
        intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER, "Unknown number");

    if (!isRinging(state)) {
      return;
    }

    if (Permissions.isReadContactsPermissionGranted(context)) {
      notifyWithContactName(context, number);
    } else {
      notifier.notify(number);
    }
  }

  private void notifyWithContactName(@NonNull final Context context, @NonNull final String number) {
    executor.submit(new Runnable() {
      @Override public void run() {
        final String contactName = getContactDisplayNameByNumber(context, number);
        final String message = contactName.isEmpty() ? number : (contactName + ", " + number);

        notifier.notify(message);
      }
    });
  }

  @NonNull
  @WorkerThread
  private String getContactDisplayNameByNumber(@NonNull Context context, @NonNull String number) {
    final Uri uri =
        Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));

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

  private boolean isRinging(@NonNull String state) {
    return TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(state);
  }
}