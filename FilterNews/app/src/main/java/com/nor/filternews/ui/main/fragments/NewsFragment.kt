package com.nor.filternews.ui.main.fragments

import android.os.Bundle
import com.nor.filternews.App
import com.nor.filternews.R
import com.nor.filternews.api.ApiBuilder
import com.nor.filternews.base.AdapterBase
import com.nor.filternews.base.FragmentBase
import com.nor.filternews.databinding.FragmentNewsBinding
import com.nor.filternews.model.News
import com.nor.filternews.model.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment : FragmentBase<FragmentNewsBinding>(), Callback<NewsResponse>, NewsItemListener {

    private lateinit var adapter: AdapterBase<News>

    override fun getLayoutId(): Int {
        return R.layout.fragment_news
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = AdapterBase(activity!!.layoutInflater, R.layout.item_news)
        binding.lvNews.adapter = adapter
        adapter.listener = this
    }

    override fun getTitle(): String {
        return App.instance!!.getString(R.string.title_news)
    }

    fun search(query: String?) {
        ApiBuilder.getInstance().search(
            query,
            "vi",
            getString(R.string.api_key)
        ).enqueue(this)
    }

    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
        // TODO
    }

    override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
        adapter.setData(response.body()?.articles)
    }

    override fun onClickItem(item: News) {
        // TODO
    }

}