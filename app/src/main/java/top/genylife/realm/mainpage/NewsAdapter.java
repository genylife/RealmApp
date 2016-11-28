package top.genylife.realm.mainpage;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.genylife.realm.mainpage.model.News;
import top.genylife.realm.mainpage.viewmodel.NewsCardVM;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private ArrayList<NewsCardVM> mDatas;

    public NewsAdapter() {
        mDatas = new ArrayList<>();
    }

    public NewsAdapter(List<News> newsList) {
        mDatas = new ArrayList<>();
        for (News news : newsList) {
            mDatas.add(news.transform());
        }
    }

    @UiThread
    public void addNews(News news) {
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
