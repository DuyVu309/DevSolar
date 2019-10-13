package com.example.duyvd1.network.api

import com.example.duyvd1.network.response.ArticlesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHelper {
    @GET("top-headlines")
    fun getListArticles(
        @Query("sources") textSearch: String,
        @Query("apiKey") apiKey: String
    ): Observable<ArticlesResponse>
}