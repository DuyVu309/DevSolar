package com.team.veza.googlenew.view.pager


import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.team.veza.googlenew.R
import com.team.veza.googlenew.adapter.NewsAdapter
import com.team.veza.googlenew.databinding.FragmentNewsBinding
import com.team.veza.googlenew.model.News
import com.team.veza.googlenew.model.repo.NewsRepository
import com.team.veza.googlenew.view.DetailActivity
import com.team.veza.googlenew.view.MainActivity
import com.team.veza.googlenew.view.dialog.DialogNews
import com.team.veza.googlenew.viewmodel.NewsViewModel

/**
 * A simple [Fragment] subclass.
 */
class FragmentNews : Fragment(), IGetData, NewsAdapter.INewsActionListener {

    private val newsAdapter = NewsAdapter()

    companion object {
        val ID_NEWS = 0
        val ID_SAVED = 1
        val ID_FAVORITE = 2
        const val TAG = "NVTFragmentNews"
    }

    var frmId = 0
    private lateinit var viewModel: NewsViewModel
    private lateinit var activity: MainActivity
    private lateinit var listData: LiveData<List<News>>
    private var binding: FragmentNewsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = this
        binding?.frmId = frmId
        initView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
        viewModel = activity.viewModel
    }

    private fun initView() {

        binding?.rvNews?.layoutManager = LinearLayoutManager(activity)
        binding?.rvNews?.setHasFixedSize(true)
        binding?.rvNews?.adapter = newsAdapter
        newsAdapter.setActionListener(this)
        initData()
    }

    private fun initData() {
        listData = viewModel.getListData(frmId)

        listData.observe(viewLifecycleOwner, Observer {
            newsAdapter.submitList(it)
            binding?.isEmpty = it.isEmpty()
        })

        if (frmId == ID_NEWS)
            viewModel.textSearch.observe(viewLifecycleOwner, Observer {
                if(it.isNotEmpty()) {
                    startGetData(viewModel.currentTabFocus, it)
                    binding?.isEmpty = false
                }else{
                    binding?.isEmpty = true
                }
            })
    }

    override fun startGetData(frmId: Int, key: String) {
        NewsRepository.getData(frmId, this, key)
    }

    override fun onGetDataStarted() {
        if (frmId != viewModel.currentTabFocus)
            return
        viewModel.setLoading(true)
    }

    override fun onGetDataCompleted() {
        if (frmId != viewModel.currentTabFocus)
            return
        viewModel.setLoading(false)
    }

    override fun onGetDataFaild() {
        //Utility.showMessage(R.string.error)
        if (frmId != viewModel.currentTabFocus)
            return
        viewModel.setLoading(false)
    }

    override fun onClickItem(v: View, news: News) {
        activity.startActivity(Intent(activity,DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_URL,news.url)
        })
    }


    override fun onLongClickItem(v: View, news: News): Boolean {
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]
        when (frmId) {
            ID_NEWS -> {
                object : DialogNews(activity, Point(x, y),frmId) {
                    override fun btnSecondClick(news: News) {
                        news.isFavorite = true
                        viewModel.dbInsert(news)
                    }

                    override fun btnFirstClick(news: News) {
                        Log.e(TAG,"News Added: $news")
                        viewModel.dbInsert(news)
                    }

                }.apply {
                    this.news = news
                    show()
                }
            }
            ID_SAVED -> {
                object : DialogNews(activity, Point(x, y),frmId) {
                    override fun btnSecondClick(news: News) {
                        viewModel.dbDelete(news)
                    }

                    override fun btnFirstClick(news: News) {
                        news.isFavorite = true
                        viewModel.dbUpdate(news)
                    }

                }.apply {
                    this.news = news
                    setTextButtonFirst(R.string.favorite)
                    setTextButtonSecond(R.string.delete)
                    show()
                }
            }
            ID_FAVORITE -> {
                object : DialogNews(activity, Point(x, y),frmId) {
                    override fun btnSecondClick(news: News) {

                    }

                    override fun btnFirstClick(news: News) {
                        viewModel.dbUpdate(news.apply { isFavorite = false })
                    }

                }.apply {
                    this.news = news
                    setTextButtonFirst(R.string.delete)
                    btnSecond.visibility = View.GONE
                    show()
                }
            }
        }

        return false
    }
}
