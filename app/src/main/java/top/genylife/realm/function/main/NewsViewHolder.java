package top.genylife.realm.function.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import top.genylife.realm.databinding.ItemNewsBinding;
import top.genylife.realm.function.model.News;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */

class NewsViewHolder extends RecyclerView.ViewHolder {


    static NewsViewHolder create(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemNewsBinding binding = ItemNewsBinding.inflate(inflater, parent, false);
        return new NewsViewHolder(binding);
    }

    private ItemNewsBinding mBinding;

    public News getNews() {
        return mNews;
    }

    private News mNews;

    private NewsViewHolder(ItemNewsBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    void bindTo(News news) {
        mNews = news;
        mBinding.setNews(news.transform());
    }

}
