package top.genylife.realm.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wanqi on 2016/11/26.
 *
 * @since 1.0.0
 */

public abstract class OnLoadMoreListener extends RecyclerView.OnScrollListener {

    private int itemCount, lastPosition, lastItemCount;

    public abstract void onLoadMore(int itemCount);

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            itemCount = layoutManager.getItemCount();
            lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        }

        if(lastItemCount != itemCount && lastPosition == itemCount - 1) {
            lastItemCount = itemCount;
            this.onLoadMore(itemCount);
        }
    }
}
