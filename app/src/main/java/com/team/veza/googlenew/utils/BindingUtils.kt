package com.team.veza.googlenew.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.team.veza.googlenew.R

object BindingUtils {
    @JvmStatic
    @BindingAdapter("url")
    fun ImageView.loadImage(url:String){
        val imageSize = Utility.getSize(R.dimen.item_size)
        Glide.with(this).load(url).apply(RequestOptions().override(imageSize))
            .into(this)
    }
}