package com.example.filternew.utils

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filternew.R
import com.example.filternew.ui.news.ArticesAdapter


@BindingAdapter("setAdapter")
fun setAdapter(view: RecyclerView, adapter: ArticesAdapter) {
    view.adapter = adapter
}


@BindingAdapter("urlToImage")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context).load(imageUrl?:"").error(R.mipmap.ic_launcher).apply(RequestOptions.centerCropTransform()).into(view)
}

