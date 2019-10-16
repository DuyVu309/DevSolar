package com.team.veza.googlenew.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team.veza.googlenew.R
import com.team.veza.googlenew.databinding.ItemNewsBinding
import com.team.veza.googlenew.model.News

class NewsAdapter : ListAdapter<News,NewsAdapter.NewsHolder>(DIFF){

    private var listener:INewsActionListener?=null

    fun setActionListener(listener: INewsActionListener){
        this.listener = listener
    }

    companion object{
        private val DIFF = object : DiffUtil.ItemCallback<News>(){
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.countId == newItem.countId
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.url == newItem.url
                        &&oldItem.title == newItem.title
                        &&oldItem.content == newItem.content
            }

        }
    }

    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        if (!::layoutInflater.isInitialized){
            layoutInflater = LayoutInflater.from(parent.context)
        }
        return NewsHolder(DataBindingUtil.inflate(layoutInflater, R.layout.item_news,parent,false))
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val pos = holder.adapterPosition
        val binding = holder.binding
        binding.news = getItem(pos)
        listener?.let {
            binding.listener = listener
            binding.cvNews.setOnLongClickListener { listener?.onLongClickItem(binding.cvNews,getItem(pos))?:true }
        }
    }

    class NewsHolder(var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    interface INewsActionListener{
        fun onClickItem(v:View,news:News)
        fun onLongClickItem(v:View,news: News):Boolean
    }
}