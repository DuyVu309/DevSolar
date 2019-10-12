package com.example.filternew.ui.news

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filternew.MyApp
import com.example.filternew.data.model.Article
import com.example.filternew.data.model.NewsResponse
import com.example.filternew.data.network.NewsRespository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRespository: NewsRespository) : ViewModel() {

    var refreshing = MutableLiveData<Boolean>()

    var news = MutableLiveData<NewsResponse>()

    var article = MutableLiveData<List<Article>>()

    var newsInitialized = MutableLiveData<Boolean>()

    var isLoading = MutableLiveData<Boolean>()

    var tag = ""

    fun getNews() {
        launch({
            news.value = newsRespository.refreshSpecifyNews(tag)
            if(news.value!=null){
                article.value = news.value!!.articles
            }
            newsInitialized.value = true
        }, {
            Toast.makeText(MyApp.context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun refreshNews() {
        refreshing.value = true
        launch({
            news.value = newsRespository.refreshSpecifyNews(tag)
            refreshing.value = false
            newsInitialized.value = true
        }, {
            Toast.makeText(MyApp.context, it.message, Toast.LENGTH_SHORT).show()
            refreshing.value = false
        })
    }

    fun onRefresh() {
        refreshNews()
    }

    fun getArticle() = newsRespository.getArticles()

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) =
        viewModelScope.launch {
            try {
                isLoading.value = true
                block()
                isLoading.value = false
            } catch (e: Throwable) {
                error(e)
                isLoading.value = false
            }
        }
}