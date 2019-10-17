package com.nor.filternews.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilder {
    companion object{
        private var instance: Api? = null

        fun getInstance(): Api {
            if (instance == null) {
                instance = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://newsapi.org/v2/")
                    .build()
                    .create(Api::class.java)
            }
            return instance!!
        }
    }
}