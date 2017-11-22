package rm.com.disturb.utils;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java8.util.Optional;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

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
    return StreamSupport.stream(receivedChunks) //
        .collect( //
            StringBuilder::new, //
            (result, item) -> result.append(item.getMessageBody()), //
            StringBuilder::append //
        ) //
        .toString();
  }

  @NonNull private static List<SmsMessage> unwrapMessage(@NonNull Intent intent) {
    //noinspection deprecation
    return Optional.ofNullable((Object[]) intent.getExtras().get(KEY_PDU_CHUNKS))
        .map(Arrays::asList)
        .map(it -> StreamSupport.stream(it)
            .map(item -> SmsMessage.createFromPdu((byte[]) item))
            .collect(Collectors.toList()))
        .orElse(Collections.emptyList());
  }
}
