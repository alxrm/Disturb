package rm.com.disturb.data.telegram.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by alex
 */

public final class UserFull {
  public static final UserFull EMPTY = new UserFull.Builder().build();

  private final String firstName;
  private final String lastName;
  private final String username;
  private final String photoUrl;

  private UserFull(@NonNull Builder builder) {
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.username = builder.username;
    this.photoUrl = builder.photoUrl;
  }

  @NonNull public String firstName() {
    return firstName;
  }

  @NonNull public String lastName() {
    return lastName;
  }

  @NonNull public String username() {
    return username;
  }

  @NonNull public String photoUrl() {
    return photoUrl;
  }

  @NonNull public Builder newBuilder() {
    return new Builder(this);
  }

  public static final class Builder {
    private String firstName;
    private String lastName;
    private String username;
    private String photoUrl;

    public Builder() {
      firstName = "";
      lastName = "";
      username = "";
      photoUrl = "";
    }

    Builder(@NonNull UserFull current) {
      firstName = current.firstName;
      lastName = current.lastName;
      username = current.username;
      photoUrl = current.photoUrl;
    }

    @NonNull public Builder firstName(@NonNull String firstName) {
      this.firstName = firstName;
      return this;
    }

    @NonNull public Builder lastName(@Nullable String lastName) {
      this.lastName = lastName == null ? "" : lastName;
      return this;
    }

    @NonNull public Builder username(@Nullable String username) {
      this.username = username == null ? "" : username;
      return this;
    }

    @NonNull public Builder photoUrl(@Nullable String photoUrl) {
      this.photoUrl = photoUrl == null ? "" : photoUrl;
      return this;
    }

    @NonNull public UserFull build() {
      return new UserFull(this);
    }
  }
}
