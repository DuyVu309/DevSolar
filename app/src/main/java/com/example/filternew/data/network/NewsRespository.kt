package com.example.filternew.data.network

import android.util.Log
import com.example.filternew.data.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRespository private constructor(private val network: NewsNetwork) {

    private suspend fun requestSpecifyNews(tag:String) = withContext(Dispatchers.IO){
        val news = network.fetchNews(tag)
        news
    }

    suspend fun refreshSpecifyNews(tag:String) = requestSpecifyNews(tag)


    companion object {

        private lateinit var instance: NewsRespository

        fun getInstance(network: NewsNetwork): NewsRespository {
            if (!::instance.isInitialized) {
                synchronized(NewsRespository::class.java) {
                    if (!::instance.isInitialized) {
                        instance = NewsRespository(network)
                    }
                }
            }
            return instance
        }

    }
}