package com.example.duyvd1.db

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.example.duyvd1.model.Articles

class ArticlesDbManager {

    private val DATABASE_NAME = "articles_db"

    private var mArticlesDb: ArticlesDataBase? = null
    private lateinit var mContext: Context
    private var mCallBackGet: OnGetDatListener? = null
    private var mCallBackDelete: OnDeleteListener? = null

    constructor(context: Context) {
        mContext = context
        mArticlesDb = Room.databaseBuilder(mContext, ArticlesDataBase::class.java, DATABASE_NAME)
            .build()

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mInstance: ArticlesDbManager? = null

        fun newInstance(context: Context): ArticlesDbManager {
            synchronized(ArticlesDbManager::class.java) {
                if (mInstance == null) mInstance = ArticlesDbManager(context)
                return mInstance!!
            }
        }
    }

    fun insertOnlySingleArticles(articles: Articles) {
        class insert : AsyncTask<Void, Void, Void?>() {
            override fun doInBackground(vararg voids: Void): Void? {
                mArticlesDb?.dataBaseAccess()?.insertOnlySingleArticles(articles)
                return null
            }
        }

        val gt = insert()
        gt.execute()
    }

    fun getListArticles(textSearch: String, callBack: OnGetDatListener) {
        mCallBackGet = callBack
        class getList : AsyncTask<Void, Void, MutableList<Articles>>() {
            override fun doInBackground(vararg voids: Void): MutableList<Articles> {
                return mArticlesDb?.dataBaseAccess()?.filterAllArticles(textSearch)!!
            }

            override fun onPostExecute(result: MutableList<Articles>?) {
                super.onPostExecute(result)
                result?.let { mCallBackGet?.onGetDataSuccess(it) }
            }
        }

        val gt = getList()
        gt.execute()
    }

    fun getListFavoriteArticles(textSearch: String, callBack: OnGetDatListener) {
        mCallBackGet = callBack
        class getList : AsyncTask<Void, Void, MutableList<Articles>>() {
            override fun doInBackground(vararg voids: Void): MutableList<Articles> {
                return mArticlesDb?.dataBaseAccess()?.filterListFavoriteArticles(textSearch)!!
            }

            override fun onPostExecute(result: MutableList<Articles>?) {
                super.onPostExecute(result)
                result?.let { mCallBackGet?.onGetDataSuccess(it) }
            }
        }

        val gt = getList()
        gt.execute()
    }

    fun updateToFavorite(articles: Articles) {
        class update : AsyncTask<Void, Void, Void?>() {
            override fun doInBackground(vararg voids: Void): Void? {
                mArticlesDb?.dataBaseAccess()?.updateArticles(articles)
                return null
            }
        }
        val gt = update()
        gt.execute()
    }

    fun deleteArticles(articles: Articles, callback: OnDeleteListener) {
        mCallBackDelete = callback
        class update : AsyncTask<Void, Void, Void?>() {
            override fun doInBackground(vararg voids: Void): Void? {
                mArticlesDb?.dataBaseAccess()?.deleteMovie(articles)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                mCallBackDelete?.onDeleteDataSuccess(articles)
            }
        }

        val gt = update()
        gt.execute()
    }

    fun removeFavorite(articles: Articles, callback: OnDeleteListener) {
        mCallBackDelete = callback
        class update : AsyncTask<Void, Void, Void?>() {
            override fun doInBackground(vararg voids: Void): Void? {
                mArticlesDb?.dataBaseAccess()?.updateArticles(articles)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                mCallBackDelete?.onDeleteDataSuccess(articles)
            }
        }

        val gt = update()
        gt.execute()
    }

    interface OnGetDatListener {
        fun onGetDataSuccess(result: MutableList<Articles>)
    }

    interface OnDeleteListener {
        fun onDeleteDataSuccess(articles: Articles)
    }
}