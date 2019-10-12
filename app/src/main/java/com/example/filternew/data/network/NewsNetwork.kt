package com.example.filternew.data.network

import android.util.Log
import com.example.filternew.data.network.api.RequestNewsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NewsNetwork {
    private val newsService = RetrofitClient.create(RequestNewsService::class.java)

    suspend fun fetchNews(tag: String) = newsService.getSpecifyNews(tag,RetrofitClient.LANGUAGE,RetrofitClient.KEY).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }
            })
        }
    }

    companion object {
        private var newsNetwork: NewsNetwork? = null

        fun getInstance(): NewsNetwork {
            if (newsNetwork == null) {
                synchronized(NewsNetwork::class.java) {
                    if (newsNetwork == null) {
                        newsNetwork =
                            NewsNetwork()
                    }
                }
            }
            return newsNetwork!!
        }

    }

}
