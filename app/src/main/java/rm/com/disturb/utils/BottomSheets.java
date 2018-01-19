package rm.com.disturb.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java8.util.Optional;
import java8.util.stream.Collectors;
import java8.util.stream.IntStreams;
import org.michaelbel.bottomsheetdialog.BottomSheet;
import rm.com.disturb.R;
import rm.com.disturb.ui.adapter.SheetAdapter;
import rm.com.disturb.ui.holder.BaseHolder.OnClickListener;
import rm.com.disturb.ui.model.SheetItem;

import static android.view.View.inflate;

/**
 * Created by alex
 */

public final class BottomSheets {
  private BottomSheets() {
    throw new IllegalStateException("No instances");
  }

  @NonNull public static BottomSheet list(@NonNull Context context, @NonNull String[] itemTitles,
      @NonNull OnClickListener<SheetItem> itemClickListener) {
    return list(context, itemTitles, new int[itemTitles.length], new int[itemTitles.length],
        itemClickListener);
  }

  @NonNull public static BottomSheet list(@NonNull Context context, @NonNull String[] itemTitles,
      @NonNull int[] itemIcons, @NonNull OnClickListener<SheetItem> itemClickListener) {
    return list(context, itemTitles, itemIcons, new int[itemTitles.length], itemClickListener);
  }

  @NonNull public static BottomSheet list(@NonNull Context context, @NonNull String[] titles,
      @NonNull int[] icons, @NonNull int[] colors,
      @NonNull OnClickListener<SheetItem> itemClickListener) {
    final BottomSheet.Builder builder = new BottomSheet.Builder(context);

    // if I could do it without atomic reference and forking his repo, I'd do it that way
    final AtomicReference<BottomSheet> sheet = new AtomicReference<>();
    final RecyclerView items = itemsOf(context, titles, icons, colors, (position, item) -> {
      Optional.ofNullable(sheet.get()).ifPresent(BottomSheet::dismiss);
      itemClickListener.onItemClick(position, item);
    });

    builder.setCustomView(items);

    final BottomSheet result = builder.show();
    sheet.set(result);

    return result;
  }

  @NonNull
  private static RecyclerView itemsOf(@NonNull Context context, @NonNull String[] itemTitles,
      @NonNull int[] itemIcons, @NonNull int[] itemColors,
      @NonNull OnClickListener<SheetItem> itemClickListener) {
    final RecyclerView list = (RecyclerView) inflate(context, R.layout.bottom_sheet_layout, null);
    final SheetAdapter adapter = new SheetAdapter();
    final List<SheetItem> sheetItems = IntStreams.range(0, itemTitles.length)
        .mapToObj(i -> new SheetItem(itemTitles[i], itemIcons[i], itemColors[i]))
        .collect(Collectors.toList());

    adapter.updateData(sheetItems);
    adapter.setOnClickListener(itemClickListener);
    list.setAdapter(adapter);

    return list;
  }
}
