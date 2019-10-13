package com.example.duyvd1.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @Expose
    @SerializedName("code")
    var code: String? = null

    @Expose
    @SerializedName("message")
    var message: String? = null

    @Expose
    @SerializedName("status")
    var status: String? = null
}