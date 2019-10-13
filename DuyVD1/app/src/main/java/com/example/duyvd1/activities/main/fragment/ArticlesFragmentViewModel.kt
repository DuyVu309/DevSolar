package com.example.duyvd1.activities.main.fragment

import android.content.Context
import com.example.duyvd1.BuildConfig
import com.example.duyvd1.MainApplication
import com.example.duyvd1.db.ArticlesDbManager
import com.example.duyvd1.model.Articles
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class ArticlesFragmentViewModel(
    private var mContext: Context?,
    private var mView: ArticlesFragmentContract.ArticlesView?
) : ArticlesFragmentContract.ArticlesViewModel, Observable() {

    private var compositeDisposable: CompositeDisposable? = CompositeDisposable()

    var mList: MutableList<Articles?> = mutableListOf()

    override fun getListArticlesFromServer(textSearch: String) {
        if (mContext == null) return
        mView?.showLoading()
        val peopleApplication = MainApplication.createApplication(mContext!!)
        val peopleService = peopleApplication.getApiHelper()

        val disposable = peopleService!!.getListArticles(textSearch, BuildConfig.API_KEY)
            .subscribeOn(peopleApplication.subscribeScheduler())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                mList.clear()
                if (it != null) mList.addAll(it.articles)
                mView?.onGetDataSuccess()
                mView?.hideLoading()
            }, {
                mView?.hideLoading()
                mView?.onGetDataError(it.toString())
            })
        compositeDisposable?.add(disposable)
    }

    override fun getListDataSaved(textSearch: String) {
        if (mContext != null) {
            mList.clear()
            ArticlesDbManager.newInstance(mContext!!)
                .getListArticles(textSearch, object : ArticlesDbManager.OnGetDatListener {
                    override fun onGetDataSuccess(result: MutableList<Articles>) {
                        mList.addAll(result)
                        mView?.onGetDataSuccess()
                    }
                })
        }
    }

    override fun getListDataFavorite(textSearch: String) {
        mList.clear()
        ArticlesDbManager.newInstance(mContext!!)
            .getListFavoriteArticles(textSearch, object :ArticlesDbManager.OnGetDatListener{
                override fun onGetDataSuccess(result: MutableList<Articles>) {
                    mList.addAll(result)
                    mView?.onGetDataSuccess()
                }
            })
        mView?.onGetDataSuccess()
    }

    override fun notifyItemDelete(articles: Articles) {
        for (i in 0 until mList.size) {
            if(articles.id == mList[i]?.id ?: false){
                mList.removeAt(i)
                mView?.onDeleteItemSuccess(i)
                break
            }
        }
    }

    override fun reset() {
        unSubscribeFromObservable()
        compositeDisposable = null
        mContext = null
    }

    private fun unSubscribeFromObservable() {
        if (compositeDisposable != null && compositeDisposable?.isDisposed == false) {
            compositeDisposable?.dispose()
        }
    }
}