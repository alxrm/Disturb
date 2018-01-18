package rm.com.disturb.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import rm.com.disturb.R;
import rm.com.disturb.ui.holder.SheetItemHolder;
import rm.com.disturb.ui.model.SheetItem;

/**
 * Created by alex
 */

public final class SheetAdapter extends BaseAdapter<SheetItem, SheetItemHolder> {
  @Override public SheetItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new SheetItemHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_bottom_sheet, parent, false));
  }
}
