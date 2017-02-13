package top.genylife.realm.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wanqi on 2016/12/13.
 *
 * @since 1.0.0
 */

public interface OnRecyclerViewItemClick {

    void onItemClick(RecyclerView.Adapter adapter, View view, RecyclerView.ViewHolder holder);
}
