package com.example.filternew.data.network.api

import com.example.filternew.data.model.NewsResponse
import com.example.filternew.data.network.RetrofitClient
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RequestNewsService {

    @GET("everything")
    fun getSpecifyNews(@Query("q") tag: String, @Query("language") language:String,@Query("apiKey") key:String): Call<NewsResponse>

}