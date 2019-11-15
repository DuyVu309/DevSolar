package com.base.application.api

import com.base.application.model.NewsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("everything")
    fun searchNews(@Query("q") q: String, @Query("apiKey") apiKey: String) : Observable<NewsResponse>
}