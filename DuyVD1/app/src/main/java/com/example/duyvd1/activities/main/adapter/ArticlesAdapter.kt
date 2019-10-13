package com.example.duyvd1.activities.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.duyvd1.R
import com.example.duyvd1.databinding.ItemArticlesBinding
import com.example.duyvd1.model.Articles
import com.example.duyvd1.activities.main.fragment.ItemArticlesViewModel
import com.example.duyvd1.utils.popup.PopupListItem

class ArticlesAdapter(private var mFragmentActivity: FragmentActivity,
                      private var mItems: MutableList<Articles?>?,
                      private var mCallBackPopup : PopupListItem.PopUpListener) :
        RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    var isTabFavorite = false

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemPeopleBinding = DataBindingUtil.inflate<ItemArticlesBinding>(LayoutInflater.from(parent.context),
                R.layout.item_articles, parent, false)
        return ViewHolder(itemPeopleBinding)
    }

    override fun getItemCount(): Int = mItems?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(mItems!![position])
    }

    inner class ViewHolder(itemBinding: ItemArticlesBinding) : RecyclerView.ViewHolder(itemBinding.llItemArticles) {
        val mBinding = itemBinding
        fun onBind(item: Articles?) {
            mBinding.viewModel = ItemArticlesViewModel(mFragmentActivity,itemView.context, item, mCallBackPopup, isTabFavorite)
        }
    }

}