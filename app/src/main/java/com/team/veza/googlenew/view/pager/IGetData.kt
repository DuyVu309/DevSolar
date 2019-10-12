package com.team.veza.googlenew.view.pager

import com.team.veza.googlenew.model.News

interface IGetData {
    fun startGetData(key:String)
    fun onGetDataStarted()
    fun onGetDataCompleted(list: List<News>)
    fun onGetDataFaild()
}