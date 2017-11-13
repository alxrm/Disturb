package rm.com.disturb.data.telegram.model;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public final class Photo {
  private String small_file_id;
  private String big_file_id;

  @NonNull public String smallFileId() {
    return small_file_id;
  }

  @NonNull public String bigFileId() {
    return big_file_id;
  }
}