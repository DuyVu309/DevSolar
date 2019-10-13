package com.example.filternew.ui.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filternew.data.model.Article
import com.example.filternew.data.model.NewsResponse
import com.example.filternew.data.network.NewsRespository
import com.example.filternew.utils.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(private val newsRespository: NewsRespository) : ViewModel() {

    var refreshing = MutableLiveData<Boolean>()

    var news = MutableLiveData<NewsResponse>()

    var article = MutableLiveData<List<Article>>()

    var articleDb = MutableLiveData<List<Article>>()

    var newsInitialized = MutableLiveData<Boolean>()

    var isLoading = MutableLiveData<Boolean>()

    var cannotAction = MutableLiveData<Boolean>()

    var tag = ""

    fun getNews() {
        launch({
            news.value = newsRespository.refreshSpecifyNews(tag)
            if (news.value != null) {
                article.value = news.value!!.articles
            }
            newsInitialized.value = true
        }, {
            Log.e(Const.SUPPER_TAG,"${it.message}")
        })
    }

    private fun refreshNews() {
        refreshing.value = true
        launch({
            news.value = newsRespository.refreshSpecifyNews(tag)
            refreshing.value = false
            newsInitialized.value = true
        }, {
            Log.e(Const.SUPPER_TAG,"${it.message}")
            refreshing.value = false
        })
    }

    fun onRefresh() {
        refreshNews()
    }

    fun insertArticle(article: Article) {
        launch({
            withContext(Dispatchers.IO) {
                newsRespository.insertArticle(article)
            }
        }, {
            Log.e(Const.SUPPER_TAG,"${it.message}")
        })

    }

    fun getArticle() = newsRespository.getArticles()

    fun deleteItem(article: Article) {
        launch({
            withContext(Dispatchers.IO) {
                newsRespository.deleteItem(article)
            }
        }, {
            Log.e(Const.SUPPER_TAG,"${it.message}")
        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                block()
                isLoading.value = false
                cannotAction.value = false
            } catch (e: Throwable) {
                error(e)
                isLoading.value = false
                cannotAction.value = true
            }
        }
}