package com.base.baselibrary.viewmodel

data class Event(var action: EventAction, var isLoading: Boolean, var error: Throwable? = null)

enum class EventAction{
    GET_USER, GET_NEWS_FEED, DOWNLOAD_IMAGE, SEARCH_NEWS
}