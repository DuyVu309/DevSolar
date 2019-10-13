package com.example.filternew.utils

import android.content.Context
import com.example.filternew.data.dao.NewsDB
import com.example.filternew.data.network.NewsNetwork
import com.example.filternew.data.network.NewsRespository
import com.example.filternew.ui.news.NewsModelFactory

object InjectorUtil {
    private fun getSpecifyNewsRepository(context: Context) = NewsRespository.getInstance(NewsDB.getInstance(context.applicationContext).articleDao(),NewsNetwork.getInstance())

    fun getSpecifyNewsFactory(context:Context) = NewsModelFactory(getSpecifyNewsRepository(context))


}