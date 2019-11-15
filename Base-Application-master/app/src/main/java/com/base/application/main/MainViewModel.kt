package com.base.application.main

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.base.application.api.ApiBuilder
import com.base.baselibrary.viewmodel.BaseViewModel
import com.base.baselibrary.viewmodel.EventAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URL

class MainViewModel(application: Application) : BaseViewModel(application) {
    val bitmap = MutableLiveData<Bitmap>()

    fun downloadImage(link: String) {
        getTask({
            val url = URL(link)
            val connection = url.openConnection()
            val b = BitmapFactory.decodeStream(connection.getInputStream())
            b
        }, EventAction.DOWNLOAD_IMAGE)
            .executeSubscribe({
                bitmap.postValue(it)
            }, subscribeOn = Schedulers.newThread(), observeOn = AndroidSchedulers.mainThread())

    }

    fun searchNews(q: String) {
        ApiBuilder.api.searchNews(q, "f70e06a71e524dfa86dbfcf7ca38e62f")
            .doRegister(EventAction.SEARCH_NEWS)
            .executeSubscribe({
                val size = it.articles.size
            })
    }
}