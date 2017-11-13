package rm.com.disturb.data.telegram.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by alex
 */

public final class Chat {
  private int id;
  private String first_name;
  private String last_name;
  private String username;
  private Photo photo;

  public int id() {
    return id;
  }

  @NonNull public String firstName() {
    return first_name;
  }

  @Nullable public String lastName() {
    return last_name;
  }

  @Nullable public String username() {
    return username;
  }

  @Nullable public Photo photo() {
    return photo;
  }
}