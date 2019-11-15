package com.base.application.model

import com.base.baselibrary.model.ModelBase

data class News(
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var content: String
) : ModelBase()

data class NewsResponse(var articles: MutableList<News>)