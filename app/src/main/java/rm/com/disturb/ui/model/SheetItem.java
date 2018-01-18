package rm.com.disturb.ui.model;

import android.support.annotation.NonNull;

/**
 * Created by alex
 */

public final class SheetItem {
  public final int icon;
  public final int color;
  public final String text;

  public SheetItem(@NonNull String text, int icon, int color) {
    this.text = text;
    this.icon = icon;
    this.color = color;
  }
}