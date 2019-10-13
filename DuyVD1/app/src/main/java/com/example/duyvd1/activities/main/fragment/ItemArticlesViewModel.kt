package com.example.duyvd1.activities.main.fragment

import android.content.Context
import android.view.View
import android.widget.ImageView

import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity

import com.bumptech.glide.Glide
import com.example.duyvd1.R
import com.example.duyvd1.activities.web.WebviewActivity
import com.example.duyvd1.db.ArticlesDbManager
import com.example.duyvd1.model.Articles
import com.example.duyvd1.utils.dialog.DialogYesNo
import com.example.duyvd1.utils.popup.PopupListItem

class ItemArticlesViewModel(
    private var fragmentActivity: FragmentActivity,
    private var mContext: Context,
    private var mArticles: Articles?,
    private var mCallBack: PopupListItem.PopUpListener,
    private var isTabFavorite: Boolean
) : BaseObservable() {

    val uriImage: String? = mArticles?.urlToImage

    val title: String? = mArticles?.title

    val desc: String? = mArticles?.description

    val date: String? = mArticles?.publishedAt.toString()

    private fun saveArticles() {
        if (mArticles != null) {
            ArticlesDbManager.newInstance(mContext).insertOnlySingleArticles(mArticles!!)
        }
    }

    fun onItemClick(view: View) {
        mContext.startActivity(WebviewActivity.newInstance(mContext, mArticles!!))
    }

    fun onItemLongClick(view: View): Boolean {
        if (mArticles?.id == null) {
            DialogYesNo.newInstance()
                .setTitle(mContext.getString(R.string.do_you_want_to_save_this_articles_to_device))
                .setPositive {
                    saveArticles()
                }
                .show(fragmentActivity.supportFragmentManager, DialogYesNo.TAG)
        } else if (mArticles?.id != null) {
            val popup = PopupListItem(mContext, mArticles, mCallBack)
            popup.setIsTabFavorite(isTabFavorite)
            popup.show(view)
        }
        return false
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String?) {
            Glide.with(imageView.context).load(url).into(imageView)
        }
    }
}
