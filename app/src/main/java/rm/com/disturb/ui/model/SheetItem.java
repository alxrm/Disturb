package rm.com.disturb.ui.model;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public final class SheetItem {
  public final int icon;
  public final String text;

  public SheetItem(@NonNull String text, int icon) {
    this.text = text;
    this.icon = icon;
  }
}