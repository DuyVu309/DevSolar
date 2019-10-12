package com.example.filternew.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val KEY = "a916706ec2d44da4942180cb6cdebb13"
    private const val BASE_URL = "https://newsapi.org/v2/"
    const val LANGUAGE = "en"

    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())

    //    val reqApi by lazy {
    //    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
    //          .addConverterFactory(GsonConverterFactory.create())
    //          .build()
    //      return@lazy retrofit.create(RequestNewsService::class.java)
    //  }

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)


}