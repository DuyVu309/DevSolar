package com.example.filternew.utils

import android.view.View
import com.example.filternew.data.model.Article

interface IOnNewsClickListener {
    fun newClicked(article:Article)
    fun newLongClicked(article: Article, itemView: View,position:Int)
}