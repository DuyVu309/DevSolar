package com.example.duyvd1.utils.popup

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean

import com.example.duyvd1.R
import com.example.duyvd1.databinding.PopupListItemBinding
import com.example.duyvd1.model.Articles
import java.util.*

class PopupListItem(private val context: Context, private val articles: Articles?, private var mCallBack :PopUpListener ) : PopupWindow() {

    var mIsTabFavorite = ObservableBoolean(false)

    init {
        setBackgroundDrawable(BitmapDrawable())
        isOutsideTouchable = true
    }


    fun setIsTabFavorite(isTabFavorite : Boolean) {
        mIsTabFavorite.set(isTabFavorite)
    }

    fun show(anchor: View) {
        isFocusable = true
        width = WindowManager.LayoutParams.WRAP_CONTENT
        height = WindowManager.LayoutParams.WRAP_CONTENT
        val popupBinding = DataBindingUtil.inflate<PopupListItemBinding>(
            LayoutInflater.from(context),
            R.layout.popup_list_item,
            null,
            false
        )
        popupBinding.popupModel = this
        contentView = popupBinding.rootPopup

        showAtLocation(
            anchor,
            Gravity.NO_GRAVITY,
            anchor.x.toInt(),
            (anchor.y + 100f + anchor.measuredHeight.toFloat()).toInt()
        )
    }

    fun addToListFavotire(view: View) {
        mCallBack.onAddToFavorite(articles)
        dismiss()
    }

    fun deleteFromListSaved(view: View) {
        mCallBack.onRemoveFromListSaved(articles)
        dismiss()
    }

    fun deleteFromListFavorite(view: View) {
        mCallBack.onRemoveFromFavorite(articles)
        dismiss()
    }

    interface PopUpListener{
        fun onAddToFavorite(articles : Articles?)

        fun onRemoveFromListSaved(articles : Articles?)

        fun onRemoveFromFavorite(articles: Articles?)
    }
}