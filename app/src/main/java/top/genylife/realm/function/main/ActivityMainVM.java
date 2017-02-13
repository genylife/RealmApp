package top.genylife.realm.function.main;

import android.support.v7.widget.LinearLayoutManager;

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
}
