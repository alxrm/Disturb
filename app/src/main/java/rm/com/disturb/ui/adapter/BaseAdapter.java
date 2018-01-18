package rm.com.disturb.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import rm.com.disturb.ui.holder.BaseHolder;

/**
 * Created by alex
 */

abstract class BaseAdapter<M, VH extends BaseHolder<M>> extends RecyclerView.Adapter<VH> {

  protected List<M> data = new ArrayList<>();
  protected BaseHolder.OnClickListener<M> clickListener;

  @Override public void onBindViewHolder(VH holder, int position) {
    if (holder != null) {
      holder.bind(data.get(position));
      holder.setOnClickListener(clickListener);
    }
  }

  @Override public int getItemCount() {
    return data.size();
  }

  final public void updateData(@NonNull List<M> data) {
    this.data = data;
    notifyDataSetChanged();
  }

  final public void add(int position, @NonNull M item) {
    this.data.add(position, item);
    notifyItemInserted(position);
  }

  final public void setOnClickListener(@NonNull BaseHolder.OnClickListener<M> clickListener) {
    this.clickListener = clickListener;
  }
}
