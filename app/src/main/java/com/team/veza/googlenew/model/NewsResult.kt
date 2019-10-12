package com.team.veza.googlenew.model

import androidx.room.Ignore
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsResult {
    @SerializedName("status")
    @Expose
    var status = ""
    @SerializedName("totalResult")
    @Expose
    var totalResult = 0
    @SerializedName("articles")
    @Expose
    var listNews:List<News>?=null

    @Ignore
    override fun toString(): String {
        return "[status: $status, totalResult: $totalResult, list: $listNews"
    }
}