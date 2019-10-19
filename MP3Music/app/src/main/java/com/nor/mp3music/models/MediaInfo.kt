package com.nor.mp3music.models

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class MediaInfo(val getFieldName: String)
