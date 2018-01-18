package rm.com.disturb.ui.holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import rm.com.disturb.R;
import rm.com.disturb.ui.model.SheetItem;

/**
 * Created by alex
 */

public final class SheetItemHolder extends BaseHolder<SheetItem> {
  @BindView(R.id.bottom_sheet_item_icon) ImageView icon;
  @BindView(R.id.bottom_sheet_item_text) TextView text;

  public SheetItemHolder(View itemView) {
    super(itemView);
  }

  @Override public void bind(@NonNull SheetItem model) {
    if (model.icon != 0) {
      icon.setImageResource(model.icon);
    } else {
      icon.setVisibility(View.GONE);
    }

    text.setText(model.text);
    itemView.setOnClickListener(v -> {
      if (clickListener != null) {
        clickListener.onItemClick(getAdapterPosition(), model);
      }
    });
  }
}
