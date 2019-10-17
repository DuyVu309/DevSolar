package com.nor.filternews.ui.main.fragments

import com.nor.filternews.base.AdapterBase
import com.nor.filternews.model.News

interface NewsItemListener : AdapterBase.ListItemListener {
    fun onClickItem(item: News)
}