package com.team.veza.googlenew.utils.retrofit

import com.team.veza.googlenew.model.NewsResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INewsRetrofitQuerry {
    @GET("everything")
    fun search(@Query("q") keySearch:String, @Query("language") language:String ="",
               @Query("apiKey") apiKey:String=RetrofitClient.API_KEY) : Call<NewsResult>
}