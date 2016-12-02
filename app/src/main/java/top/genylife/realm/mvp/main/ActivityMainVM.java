package top.genylife.realm.mvp.main;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import top.genylife.realm.listener.OnLoadMoreListener;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */

public class ActivityMainVM {

    @Inject
    public NewsAdapter adapter;

    @Inject
    public LinearLayoutManager layoutManager;

    public OnLoadMoreListener scrollListener;

    @Inject
    public ActivityMainVM() {

    }

    @BindingAdapter({"adapter"})
    public static void setRecyclerViewAdapter(RecyclerView view, RecyclerView.Adapter adapter) {
        view.setAdapter(adapter);
    }

    @BindingAdapter({"loadmore"})
    public static void setRecyclerViewLoadMore(RecyclerView view, OnLoadMoreListener listener) {
        view.addOnScrollListener(listener);
    }
}
