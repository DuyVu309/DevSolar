package com.team.veza.googlenew.model.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.team.veza.googlenew.database.NewsDB
import com.team.veza.googlenew.database.NewsDao
import com.team.veza.googlenew.model.News
import com.team.veza.googlenew.model.NewsResult
import com.team.veza.googlenew.utils.retrofit.RetrofitClient
import com.team.veza.googlenew.view.pager.FragmentNews
import com.team.veza.googlenew.view.pager.IGetData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NewsRepository {
    val TAG = "NVT" + javaClass.simpleName
    val RESULT_FAILD = 0
    val RESULT_SUCCESSFUL = 1
    val newsDao = NewsDB.getInstance().newsDao()

    fun getData(pagerId: Int, getDataListener: IGetData, key: String = "") {
        when (pagerId) {
            FragmentNews.ID_NEWS -> {
                getDataListener.onGetDataStarted()
                RetrofitClient.getNewsClient().search(key).enqueue(object : Callback<NewsResult> {
                    override fun onResponse(
                        call: Call<NewsResult>?,
                        response: Response<NewsResult>?
                    ) {
                        Log.e(TAG, "Successful")
                        if (response?.isSuccessful == true) {
                            getDataListener.onGetDataCompleted(
                                response.body().listNews ?: ArrayList()
                            )
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
            FragmentNews.ID_SAVED -> {
                getDataListener.onGetDataStarted()
                GlobalScope.launch(Dispatchers.IO) {
                    val list = newsDao.get("%$key%").value ?: ArrayList()
                    withContext(Dispatchers.Main) {
                        getDataListener.onGetDataCompleted(list)
                    }
                }
            }
            FragmentNews.ID_FAVORITE -> {
                getDataListener.onGetDataStarted()
                GlobalScope.launch(Dispatchers.IO) {
                    val list = newsDao.getFavorite("%$key%").value ?: ArrayList()
                    withContext(Dispatchers.Main) {
                        getDataListener.onGetDataCompleted(list)
                    }
                }
            }
        }
    }

    fun dbInsert(news: News) = GlobalScope.launch(Dispatchers.IO) { newsDao.insert(news) }
    fun dbDelelte(news: News) = GlobalScope.launch(Dispatchers.IO) { newsDao.delete(news) }
    fun dbUpdate(news: News) = GlobalScope.launch(Dispatchers.IO) { newsDao.update(news) }
}