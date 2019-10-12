package com.example.filternew.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.filternew.R
import com.example.filternew.data.model.Article
import com.example.filternew.databinding.ItemNewsBindingImpl
import com.example.filternew.utils.IOnNewsClickListener


class ArticesAdapter : RecyclerView.Adapter<ArticesAdapter.ItemArticleBindingHolder>() {

    private var listener:IOnNewsClickListener ? =null
    private var artices: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemArticleBindingHolder {
        val binding = DataBindingUtil.inflate<ItemNewsBindingImpl>(
            LayoutInflater.from(parent.context),
            R.layout.item_news,
            parent,
            false
        )
        return ItemArticleBindingHolder(binding)
    }

    override fun getItemCount(): Int {
        return artices.size
    }

    override fun onBindViewHolder(itemArticleBindingHolder: ItemArticleBindingHolder, position: Int) {
        val article = artices[itemArticleBindingHolder.adapterPosition]
        itemArticleBindingHolder.bind(article)
        itemArticleBindingHolder.itemBinding.listener = listener
        itemArticleBindingHolder.itemView.setOnLongClickListener {
            listener?.newLongClicked(article,itemArticleBindingHolder.ivNews,itemArticleBindingHolder.adapterPosition)
            true
        }

    }

    fun setArticleList(list:List<Article>){
        this.artices = list as ArrayList<Article>
        notifyDataSetChanged()
    }



    fun setListener(listener:IOnNewsClickListener){
        this.listener = listener
    }

    inner class ItemArticleBindingHolder( var itemBinding: ItemNewsBindingImpl) : RecyclerView.ViewHolder(itemBinding.root){
        val ivNews: ImageView = itemView.findViewById(R.id.iv_news)
            fun bind(article :Article){
                itemBinding.article = article
                itemBinding.executePendingBindings()
            }
    }


}

