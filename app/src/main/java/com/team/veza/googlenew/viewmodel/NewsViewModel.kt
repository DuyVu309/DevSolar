package com.team.veza.googlenew.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.veza.googlenew.model.News
import com.team.veza.googlenew.model.repo.NewsRepository

class NewsViewModel : ViewModel(){

    private val TAG = "NVT"+javaClass.simpleName

    var isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    var currentTabFocus = 0
    var textSearch = MutableLiveData<String>().apply {
        value = ""
    }

    fun getListData(id:Int) = NewsRepository.listData[id]

    fun setLoading(isShown: Boolean){
        isShowLoading.value = isShown
    }

    fun dbInsert(news: News){
        NewsRepository.dbInsert(news)
    }

    fun dbDelete(news:News){
        NewsRepository.dbDelete(news)
    }

    fun dbUpdate(news:News){
        NewsRepository.dbUpdate(news)
    }
}