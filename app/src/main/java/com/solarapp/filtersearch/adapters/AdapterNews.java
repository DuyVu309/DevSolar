package com.solarapp.filtersearch.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.solarapp.filtersearch.R;
import com.solarapp.filtersearch.databinding.ItemNewsBinding;
import com.solarapp.filtersearch.interfaces.IItemNewsAdapterListener;
import com.solarapp.filtersearch.models.ArticlesItem;

import java.util.ArrayList;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> implements IItemNewsAdapterListener {
    private ArrayList<ArticlesItem> articlesItems;
    private INewsCallback mCallback;
    public void setArticlesList(ArrayList<ArticlesItem> articlesItems) {
        this.articlesItems = articlesItems;
        notifyDataSetChanged();
    }

    public void onCallback(INewsCallback mCallback) {
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_news, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArticlesItem dataModel = articlesItems.get(position);
//        holder.bind(dataModel);
        holder.itemNewsBinding.setModel(dataModel);
        holder.itemNewsBinding.setItemListener(this);
        holder.itemNewsBinding.itemNews.setOnLongClickListener(view -> true);
    }

    @Override
    public int getItemCount() {
        if (articlesItems != null) {
            return articlesItems.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onClickNewsListener(ArticlesItem data) {
        //web view
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding itemNewsBinding;

        ViewHolder(ItemNewsBinding itemNewsBinding) {
            super(itemNewsBinding.getRoot());
            this.itemNewsBinding = itemNewsBinding;
        }

//        void bind(Object obj) {
//            itemNewsBinding.setVariable(BR.model, obj);
//            itemNewsBinding.executePendingBindings();
//        }
    }

    public interface INewsCallback {
        void onDownloadNews(ArticlesItem item);
    }

}
