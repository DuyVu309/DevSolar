package com.team.veza.googlenew.utils.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    val API_KEY = "6101dc5c3c614bd68a4d170c104b1de0"
    private val baseUrl = "https://newsapi.org/v2/"
    private val retrofit = Retrofit.Builder().baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val newsClient = retrofit.create(INewsRetrofitQuerry::class.java)

    fun getNewsClient() = newsClient

}