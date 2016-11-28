package top.genylife.realm.mainpage.viewmodel;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import top.genylife.realm.OnLoadMoreListener;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */

public class ActivityMainVM {

    public RecyclerView.Adapter adapter;

    public RecyclerView.LayoutManager layoutManager;

    public OnLoadMoreListener scrollListener;

    @BindingAdapter({"adapter"})
    public static void setRecyclerViewAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter({"loadmore"})
    public static void setRecyclerViewLoadMore(RecyclerView view, OnLoadMoreListener listener) {
        view.addOnScrollListener(listener);
    }
}
