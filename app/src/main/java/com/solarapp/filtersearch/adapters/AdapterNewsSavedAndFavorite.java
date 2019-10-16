package com.solarapp.filtersearch.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.solarapp.filtersearch.R;
import com.solarapp.filtersearch.data.News;
import com.solarapp.filtersearch.databinding.ItemNewsSavedAndFavoriteBinding;
import com.solarapp.filtersearch.interfaces.IItemNewsAdapterListener;
import com.solarapp.filtersearch.model.ArticlesItem;

import java.util.ArrayList;

public class AdapterNewsSavedAndFavorite extends RecyclerView.Adapter<AdapterNewsSavedAndFavorite.ViewHolder> implements IItemNewsAdapterListener {
    private ArrayList<News> newsArrayList;
    private INewsCallback mCallback;


    public void setNewsArrayList(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;
        notifyDataSetChanged();
    }


    public void onCallback(INewsCallback mCallback) {
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsSavedAndFavoriteBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_news_saved_and_favorite, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News dataModel = newsArrayList.get(position);
//        holder.bind(dataModel);
        holder.itemBinding.setModel(dataModel);
        holder.itemBinding.setItemListener(this);
        holder.itemBinding.itemNews.setOnLongClickListener(view -> {
            //show menu


            return true;
        });
    }

    @Override
    public int getItemCount() {
        if (newsArrayList != null) {
            return newsArrayList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onItemNewsClick(ArticlesItem data) {
        //not use
    }

    @Override
    public void onItemNewsSavedAndFavorite(News news) {
        //web view
    }


    public interface INewsCallback {
        void onSaveNews(News news);

        void onDeleteNews(News news);

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsSavedAndFavoriteBinding itemBinding;

        ViewHolder(ItemNewsSavedAndFavoriteBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

//        void bind(Object obj) {
//            itemNewsBinding.setVariable(BR.model, obj);
//            itemNewsBinding.executePendingBindings();
//        }
    }
}
