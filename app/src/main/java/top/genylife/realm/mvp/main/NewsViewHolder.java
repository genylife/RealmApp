package top.genylife.realm.mvp.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import top.genylife.realm.databinding.ItemNewsBinding;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */

public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    static NewsViewHolder create(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemNewsBinding binding = ItemNewsBinding.inflate(inflater, parent, false);
        return new NewsViewHolder(binding);
    }

    private ItemNewsBinding mBinding;

    private NewsViewHolder(ItemNewsBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
        itemView.setOnClickListener(this);
    }

    public void bindTo(NewsCardVM news) {
        mBinding.setNews(news);
    }


    @Override
    public void onClick(View v) {

    }
}
