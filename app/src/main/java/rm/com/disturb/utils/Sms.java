package rm.com.disturb.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import java.util.List;

import static rm.com.disturb.utils.Lists.listOfArray;
import static rm.com.disturb.utils.Lists.map;
import static rm.com.disturb.utils.Lists.reduce;

/**
 * Created by alex
 */

public final class Sms {
  private static final String KEY_PDU_CHUNKS = "pdus";

  private Sms() {
    throw new IllegalStateException("No instances");
  }

  @NonNull public static String textOf(@NonNull Intent intent) {
    final List<SmsMessage> chunks = unwrapMessage(intent);

    return unwrapMessageText(chunks);
  }

  @NonNull public static String numberOf(@NonNull Intent intent) {
    final List<SmsMessage> chunks = unwrapMessage(intent);

    return chunks.get(0).getOriginatingAddress();
  }

  @NonNull private static String unwrapMessageText(@NonNull List<SmsMessage> receivedChunks) {
    return reduce(receivedChunks, "", (result, item) -> result + item.getMessageBody());
  }

  @NonNull private static List<SmsMessage> unwrapMessage(@NonNull Intent intent) {
    final List<Object> pduChunks = listOfArray((Object[]) intent.getExtras().get(KEY_PDU_CHUNKS));

    return map(pduChunks, item -> {
      //noinspection deprecation
      return SmsMessage.createFromPdu((byte[]) item);
    });
  }
}
