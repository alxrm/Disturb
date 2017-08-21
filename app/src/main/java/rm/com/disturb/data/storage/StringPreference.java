package rm.com.disturb.data.storage;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public final class StringPreference implements Preference<String> {
  private final SharedPreferences preferences;
  private final String key;
  private final String defaultValue;

  public StringPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
    this(preferences, key, "");
  }

  public StringPreference(@NonNull SharedPreferences preferences, @NonNull String key,
      @NonNull String defaultValue) {
    this.preferences = preferences;
    this.key = key;
    this.defaultValue = defaultValue;
  }

  @Override @NonNull public String get() {
    return preferences.getString(key, defaultValue);
  }

  @Override public boolean isSet() {
    return preferences.contains(key);
  }

  @Override public void set(@NonNull String value) {
    preferences.edit().putString(key, value).apply();
  }

  @Override public void delete() {
    preferences.edit().remove(key).apply();
  }
}
