package com.team.veza.googlenew.view.pager

import androidx.lifecycle.LiveData
import com.team.veza.googlenew.model.News

interface IGetData {
    fun startGetData(frmId:Int,key:String)
    fun onGetDataStarted()
    fun onGetDataCompleted()
    fun onGetDataFaild()
}