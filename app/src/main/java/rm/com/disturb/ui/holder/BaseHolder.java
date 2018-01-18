package rm.com.disturb.ui.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;

/**
 * Created by alex
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {

  protected OnClickListener<T> clickListener;

  public BaseHolder(View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  abstract public void bind(@NonNull T model);

  public final void setOnClickListener(@Nullable OnClickListener<T> clickListener) {
    this.clickListener = clickListener;
  }

  public interface OnClickListener<T> {
    void onItemClick(int position, @NonNull T item);
  }
}