package com.team.veza.googlenew.model.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.team.veza.googlenew.database.NewsDB
import com.team.veza.googlenew.model.News
import com.team.veza.googlenew.model.NewsResult
import com.team.veza.googlenew.utils.retrofit.RetrofitClient
import com.team.veza.googlenew.view.pager.FragmentNews
import com.team.veza.googlenew.view.pager.IGetData
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NewsRepository {
    val TAG = "NVT" + javaClass.simpleName
    val RESULT_FAILD = 0
    val RESULT_SUCCESSFUL = 1
    val newsDao = NewsDB.getInstance().newsDao()

//    var listNews: LiveData<List<News>> = MutableLiveData<List<News>>().apply {
//        value = ArrayList()
//    }

    var listNews = MutableLiveData<List<News>>()

    var listSaved: LiveData<List<News>> = newsDao.get()
    var listFavorite: LiveData<List<News>> = newsDao.getFavorite()

    val listData = ArrayList<LiveData<List<News>>>().apply {
        add(listNews)
        add(listSaved)
        add(listFavorite)
    }

    fun getData(pagerId: Int, getDataListener: IGetData, key: String = "") {
        when (pagerId) {
            FragmentNews.ID_NEWS -> {
                getDataListener.onGetDataStarted()
                RetrofitClient.getNewsClient().search(key).enqueue(object : Callback<NewsResult> {
                    override fun onResponse(
                        call: Call<NewsResult>?,
                        response: Response<NewsResult>?
                    ) {
                        if (response?.isSuccessful == true) {
                            val listResult = response.body().listNews ?: ArrayList()
                            Log.e(TAG, "Successful with Result: ${listResult[0].urlToImage}")
                            getDataListener.onGetDataCompleted()
                            listNews.value = listResult

                        } else {
                            getDataListener.onGetDataFaild()
                        }
                    }

                    override fun onFailure(call: Call<NewsResult>?, t: Throwable?) {
                        Log.e(TAG, "GETDATA: id=$pagerId, Faild: ${t?.message}")
                        getDataListener.onGetDataFaild()
                    }

                })
            }
//            FragmentNews.ID_SAVED -> {
//                getDataListener.onGetDataStarted()
//                getDataListener.onGetDataCompleted(newsDao.get())
////                GlobalScope.launch(Dispatchers.IO) {
////                    withContext(Dispatchers.Main) {
////                    }
////                }
//            }
//            FragmentNews.ID_FAVORITE -> {
//                getDataListener.onGetDataStarted()
//                getDataListener.onGetDataCompleted(newsDao.getFavorite(""))
////                GlobalScope.launch(Dispatchers.IO) {
////                    withContext(Dispatchers.Main) {
////                        getDataListener.onGetDataCompleted(newsDao.getFavorite(""))
////                    }
////                }
//            }
        }
    }

    fun dbInsert(news: News) = GlobalScope.launch {
        val id = newsDao.insert(news)
        Log.e(TAG, "Added Id: $id,List db = ${newsDao.get().value}")
    }

    fun dbDelete(news: News) = GlobalScope.launch { newsDao.delete(news) }
    fun dbUpdate(news: News) = GlobalScope.launch { newsDao.update(news) }
}