package com.example.duyvd1.activities.main.fragment

import com.example.duyvd1.model.Articles

class ArticlesFragmentContract {
    interface ArticlesView{
        fun onGetDataSuccess()

        fun onGetDataError(message : String)

        fun onDeleteItemSuccess(position : Int)

        fun showLoading()

        fun hideLoading()
    }

    interface ArticlesViewModel{
        fun getListArticlesFromServer(textSearch :String)

        fun getListDataSaved(textSearch :String)

        fun getListDataFavorite(textSearch :String)

        fun notifyItemDelete(articles: Articles)

        fun reset()

    }
}