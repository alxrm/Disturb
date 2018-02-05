package rm.com.disturb.utils;

import android.support.annotation.NonNull;
import java.util.List;
import java8.util.stream.Collectors;
import java8.util.stream.IntStreams;
import rm.com.disturb.ui.model.SheetItem;

/**
 * Created by alex
 */

public final class BottomSheets {
  private BottomSheets() {
    throw new IllegalStateException("No instances");
  }

  @NonNull public static List<SheetItem> sheetItemsOf(@NonNull String[] itemTitles,
      @NonNull int[] itemIcons) {
    return IntStreams.range(0, itemTitles.length)
        .mapToObj(i -> new SheetItem(itemTitles[i], itemIcons[i]))
        .collect(Collectors.toList());
  }
}
