package com.team.veza.googlenew.view.pager


import android.content.Context
import android.graphics.Point
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import com.team.veza.googlenew.R
import com.team.veza.googlenew.adapter.NewsAdapter
import com.team.veza.googlenew.databinding.FragmentNewsBinding
import com.team.veza.googlenew.model.News
import com.team.veza.googlenew.model.repo.NewsRepository
import com.team.veza.googlenew.utils.Utility
import com.team.veza.googlenew.view.MainActivity
import com.team.veza.googlenew.view.dialog.DialogNews
import com.team.veza.googlenew.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * A simple [Fragment] subclass.
 */
class FragmentNews : Fragment(), IGetData,NewsAdapter.INewsActionListener {

    private val newsAdapter = NewsAdapter()

    companion object {
        val ID_NEWS = 0
        val ID_SAVED = 1
        val ID_FAVORITE = 2
    }

    var frmId = 0
    private lateinit var viewModel: NewsViewModel
    private lateinit var activity: MainActivity
    private lateinit var listData: MutableLiveData<List<News>>
    private var binding:FragmentNewsBinding?=null

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
        binding?.frmId = frmId
        initView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
        viewModel = activity.viewModel
    }

    private fun initView() {
        rv_news.layoutManager = LinearLayoutManager(activity)
        rv_news.setHasFixedSize(true)
        rv_news.adapter = newsAdapter
        newsAdapter.setActionListener(this)
        initData()
    }

    private fun initData() {
        listData = viewModel.listData[frmId]
        listData.observe(viewLifecycleOwner, Observer {
            newsAdapter.submitList(it)
            binding?.isEmpty = it.isEmpty()
        })
        viewModel.textSearch.observe(viewLifecycleOwner, Observer {
            startGetData(it)
        })
    }

    override fun startGetData(key:String) {
        NewsRepository.getData(viewModel.currentTabFocus, this, key)
    }

    override fun onGetDataStarted() {
        viewModel.setLoading(true)
    }

    override fun onGetDataCompleted(list: List<News>) {
        viewModel.setListValueById(viewModel.currentTabFocus, list)
        viewModel.setLoading(false)
    }

    override fun onGetDataFaild() {
        //Utility.showMessage(R.string.error)
        viewModel.setListValueById(viewModel.currentTabFocus, ArrayList())
        viewModel.setLoading(false)
    }

    override fun onClickItem(v: View, news: News) {
        Utility.showMessage("Clicked $news")
    }

    override fun onLongClickItem(v: View, news: News): Boolean {
        val location = IntArray(2)
        v.getLocationOnScreen(location)
        val x = location[0]
        val y = location[1]
        when(frmId){
            ID_NEWS->{
                object : DialogNews(activity, Point(x,y)){
                    override fun btnSecondClick(news: News) {
                        news.isFavorite = true
                        NewsRepository.dbInsert(news)
                    }

                    override fun btnFirstClick(news: News) {
                        NewsRepository.dbInsert(news)
                    }

                }.apply {
                    this.news = news
                    show()
                }
            }
            ID_SAVED->{
                object : DialogNews(activity, Point(x,y)){
                    override fun btnSecondClick(news: News) {
                        NewsRepository.dbDelelte(news)
                    }

                    override fun btnFirstClick(news: News) {
                        news.isFavorite = true
                        NewsRepository.dbUpdate(news)
                    }

                }.apply {
                    this.news = news
                    setTextButtonFirst(R.string.favorite)
                    setTextButtonSecond(R.string.delete)
                    show()
                }
            }
            ID_FAVORITE->{
                object : DialogNews(activity, Point(x,y)){
                    override fun btnSecondClick(news: News) {

                    }

                    override fun btnFirstClick(news: News) {
                        NewsRepository.dbUpdate(news.apply { isFavorite = false })
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
