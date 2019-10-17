package com.nor.filternews.api

import com.nor.filternews.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("everything")
    fun search(@Query("q") key: String?,
               @Query("language") language: String,
               @Query("apiKey") apiKey: String) : Call<NewsResponse>
}