package com.team.veza.googlenew.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.team.veza.googlenew.model.News

class NewsViewModel : ViewModel(){
    var isShowLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    var currentTabFocus = 0
    var textSearch = MutableLiveData<String>().apply {
        value = ""
    }
    var listNews = MutableLiveData<List<News>>().apply {
        value = ArrayList()
    }
    var listSaved = MutableLiveData<List<News>>().apply {
        value = ArrayList()
    }
    var listFavorite = MutableLiveData<List<News>>().apply {
        value = ArrayList()
    }

    val listData = ArrayList<MutableLiveData<List<News>>>().apply {
        add(listNews)
        add(listSaved)
        add(listFavorite)
    }

    fun setListValueById(id:Int,list: List<News>){
        listData[id].value = list
    }

    fun getListSize(frmId:Int) = listData[frmId].value?.size?:0

    fun setLoading(isShown: Boolean){
        isShowLoading.value = isShown
    }
}