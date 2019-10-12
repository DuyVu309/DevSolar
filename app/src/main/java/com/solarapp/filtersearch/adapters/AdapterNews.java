package com.solarapp.filtersearch.adapters;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.solarapp.filtersearch.R;
import com.solarapp.filtersearch.databinding.ItemNewsBinding;
import com.solarapp.filtersearch.interfaces.IItemNewsAdapterListener;
import com.solarapp.filtersearch.models.Response;

import java.lang.reflect.Field;
import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder> implements IItemNewsAdapterListener {
    private List<Response> dataModelList;
    private Activity context;

    public AdapterNews(List<Response> dataModelList, Activity context) {
        this.dataModelList = dataModelList;
        this.context = context;
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
        Response dataModel = dataModelList.get(position);
        holder.bind(dataModel);
        holder.itemNewsBinding.setItemListener(this);
        holder.itemNewsBinding.itemNews.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    @Override
    public void onClickNewsListener(Response data) {
        //web view
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemNewsBinding itemNewsBinding;

        public ViewHolder(ItemNewsBinding itemNewsBinding) {
            super(itemNewsBinding.getRoot());
            this.itemNewsBinding = itemNewsBinding;
        }

        public void bind(Object obj) {
            itemNewsBinding.setVariable(com.solarapp.filtersearch.BR.model, obj);
            itemNewsBinding.executePendingBindings();
        }
    }


}
