package rm.com.disturb.data.telegram.response;

import java.util.Locale;
import rm.com.disturb.data.telegram.model.FileData;

/**
 * Created by alex
 */

public final class FileResponse extends TelegramResponse<FileData> {
  @Override public String toString() {
    return String.format(Locale.US, "MessageResponse {\nok=%s;\nfileId=%s;\nfilePath=%s\n}", isOk(),
        data().fileId(), data().filePath());
  }
}
