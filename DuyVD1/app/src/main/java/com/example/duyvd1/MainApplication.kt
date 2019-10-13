package com.example.duyvd1

import android.app.Application
import android.content.Context
import com.example.duyvd1.network.api.ApiHelper
import com.example.duyvd1.network.api.ApiManager
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class MainApplication : Application() {

    private var mApiHelper: ApiHelper? = null
    private var scheduler: Scheduler? = null

    companion object {
        var mApplication: Application? = null

        fun createApplication(context: Context): MainApplication {
            return context.applicationContext as MainApplication
        }
    }

    fun getApiHelper(): ApiHelper? {
        if (mApiHelper == null) {
            mApiHelper = ApiManager.onCreate()
        }
        return mApiHelper
    }

    fun subscribeScheduler(): Scheduler? {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }

        return scheduler
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = MainApplication()
    }
}