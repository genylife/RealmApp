package top.genylife.realm.mvp.main;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import top.genylife.realm.Transform;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private ArrayList<NewsCardVM> mDatas;

    @Inject
    public NewsAdapter() {
        mDatas = new ArrayList<>();
    }

    @UiThread
    public void addNews(Transform<NewsCardVM> news) {
        NewsCardVM vm = news.transform();
        mDatas.add(vm);
        notifyItemInserted(mDatas.size());
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NewsViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        holder.bindTo(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
