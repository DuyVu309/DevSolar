package com.example.filternew.data.network

import android.util.Log
import com.example.filternew.data.dao.ArticleDao
import com.example.filternew.data.model.Article
import com.example.filternew.data.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRespository private constructor(private val articleDao: ArticleDao, private val network: NewsNetwork) {

    private suspend fun requestSpecifyNews(tag:String) = withContext(Dispatchers.IO){
        val news = network.fetchNews(tag)
        news
    }

    suspend fun refreshSpecifyNews(tag:String) = requestSpecifyNews(tag)

    // Article DAO
    fun getArticles() = articleDao.getArticles()

    fun insertArticle(article: Article) = articleDao.insertArticle(article)

    fun deleteAll() = articleDao.deleteAll()

    fun deleteItem(article: Article) = articleDao.deteleItem(article)

    companion object {
        private lateinit var instance: NewsRespository
        fun getInstance(articleDao: ArticleDao,network: NewsNetwork): NewsRespository {
            if (!::instance.isInitialized) {
                synchronized(NewsRespository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = NewsRespository(articleDao,network)
                    }
                }
            }
            return instance
        }

    }
}