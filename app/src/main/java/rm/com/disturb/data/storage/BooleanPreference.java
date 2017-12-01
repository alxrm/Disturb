package rm.com.disturb.data.storage;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public final class BooleanPreference implements Preference<Boolean> {
  private final SharedPreferences preferences;
  private final String key;
  private final Boolean defaultValue;

  public BooleanPreference(@NonNull SharedPreferences preferences, @NonNull String key) {
    this(preferences, key, Boolean.FALSE);
  }

  public BooleanPreference(@NonNull SharedPreferences preferences, @NonNull String key,
      @NonNull Boolean defaultValue) {
    this.preferences = preferences;
    this.key = key;
    this.defaultValue = defaultValue;
  }

  @Override @NonNull public Boolean get() {
    return preferences.getBoolean(key, defaultValue);
  }

  @Override public boolean isSet() {
    return preferences.contains(key);
  }

  @Override public void set(@NonNull Boolean value) {
    preferences.edit().putBoolean(key, value).apply();
  }

  @Override public void delete() {
    preferences.edit().remove(key).apply();
  }
}
