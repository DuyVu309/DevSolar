package com.example.duyvd1.activities.main

import android.content.Context
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import com.example.duyvd1.R
import com.example.duyvd1.activities.main.fragment.ArticlesFragment
import java.util.*

class MainViewModel(private var mView: MainContract.MainView, private var mContext: Context) :
    MainContract.MainViewModel, Observable() {

    var mIsSearch = ObservableBoolean(false)

    override fun getListTab(): MutableList<String> {
        return mutableListOf(
            mContext.getString(R.string.tintuc),
            mContext.getString(R.string.daluu),
            mContext.getString(R.string.yeuthich)
        )
    }

    fun getListFragment(): MutableList<Fragment> {
        return mutableListOf(
            ArticlesFragment.newInstance(),
            ArticlesFragment.newInstance(),
            ArticlesFragment.newInstance()
        )
    }

    override fun onDestroy() {

    }

    fun onCloseSearch(view: View) {
        mIsSearch.set(false)
        mView.setEmptyText()
    }

    fun onOpenSearch(view: View) {
        mIsSearch.set(true)
    }
}