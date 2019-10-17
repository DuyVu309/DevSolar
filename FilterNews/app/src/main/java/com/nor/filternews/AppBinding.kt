package com.nor.filternews

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.nor.filternews.model.News

object AppBinding {

    @JvmStatic
    @BindingAdapter("setImageUrl")
    fun setImageUrl(im: ImageView, item: News) {
        Glide.with(im).load(item.urlToImage).into(im)
    }
}