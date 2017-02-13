package top.genylife.realm.databinding.adapters;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import top.genylife.realm.listener.OnLoadMoreListener;

/**
 * Created by wanqi on 2016/12/13.
 *
 * @since 1.0.0
 */

public class RecyclerViewBindingAdapter {

    @BindingAdapter({"adapter"})
    public static void setAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter({"loadmore"})
    public static void setLoadMore(RecyclerView view, OnLoadMoreListener listener) {
        view.addOnScrollListener(listener);
    }
}
