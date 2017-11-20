package rm.com.disturb.data.telegram.model;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public final class FileData {
  private String file_id;

  // https://api.telegram.org/file/bot{TOKEN}/profile_photos/file_2.jpg
  private String file_path;
  private long file_size;

  @NonNull public final String fileId() {
    return file_id;
  }

  @NonNull public final String filePath() {
    return file_path;
  }

  public final long size() {
    return file_size;
  }
}
