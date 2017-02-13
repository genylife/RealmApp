package top.genylife.realm.function.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import javax.inject.Inject;

import top.genylife.realm.listener.OnRecyclerViewItemClick;
import top.genylife.realm.function.model.News;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private ArrayList<News> mDatas;

    public void setOnItemClick(OnRecyclerViewItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    private OnRecyclerViewItemClick onItemClick;

    @Inject
    public NewsAdapter() {
        mDatas = new ArrayList<>();
    }

    public void addNews(News news) {
        mDatas.add(news);
        notifyItemInserted(mDatas.size());
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NewsViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final NewsViewHolder holder, int position) {
        holder.bindTo(mDatas.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(NewsAdapter.this, v, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
