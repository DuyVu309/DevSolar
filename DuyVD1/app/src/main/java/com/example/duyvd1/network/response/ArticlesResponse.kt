package com.example.duyvd1.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.example.duyvd1.model.Articles
import com.example.duyvd1.network.response.BaseResponse

data class ArticlesResponse(
    @Expose
    @SerializedName("articles")
    var articles: MutableList<Articles> = mutableListOf()
) : BaseResponse()
