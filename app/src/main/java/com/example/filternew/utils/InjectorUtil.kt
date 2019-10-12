package com.example.filternew.utils

import com.example.filternew.MyApp.Companion.context
import com.example.filternew.data.dao.NewsDB
import com.example.filternew.data.network.NewsNetwork
import com.example.filternew.data.network.NewsRespository
import com.example.filternew.ui.MainModelFactory
import com.example.filternew.ui.news.NewsModelFactory

object InjectorUtil {
    private fun getSpecifyNewsRepository() = NewsRespository.getInstance(NewsDB.getInstance(context).articleDao(),NewsNetwork.getInstance())

    fun getSpecifyNewsFactory() = NewsModelFactory(getSpecifyNewsRepository())

    fun getMainModelFactory() = MainModelFactory(getSpecifyNewsRepository())


}